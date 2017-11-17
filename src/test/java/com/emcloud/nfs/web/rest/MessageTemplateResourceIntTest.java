package com.emcloud.nfs.web.rest;

import com.emcloud.nfs.EmCloudNfsApp;

import com.emcloud.nfs.config.SecurityBeanOverrideConfiguration;

import com.emcloud.nfs.domain.MessageTemplate;
import com.emcloud.nfs.repository.MessageTemplateRepository;
import com.emcloud.nfs.service.MessageTemplateService;
import com.emcloud.nfs.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.emcloud.nfs.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MessageTemplateResource REST controller.
 *
 * @see MessageTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudNfsApp.class, SecurityBeanOverrideConfiguration.class})
public class MessageTemplateResourceIntTest {

    private static final String DEFAULT_OID = "AAAAAAAAAA";
    private static final String UPDATED_OID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PARAM_FLAG = false;
    private static final Boolean UPDATED_PARAM_FLAG = true;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final String DEFAULT_SMS_SEND_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_SMS_SEND_CHANNEL = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE = true;
    private static final Boolean UPDATED_ENABLE = false;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MessageTemplateRepository messageTemplateRepository;

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMessageTemplateMockMvc;

    private MessageTemplate messageTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MessageTemplateResource messageTemplateResource = new MessageTemplateResource(messageTemplateService);
        this.restMessageTemplateMockMvc = MockMvcBuilders.standaloneSetup(messageTemplateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageTemplate createEntity(EntityManager em) {
        MessageTemplate messageTemplate = new MessageTemplate()
            .oid(DEFAULT_OID)
            .content(DEFAULT_CONTENT)
            .paramFlag(DEFAULT_PARAM_FLAG)
            .type(DEFAULT_TYPE)
            .smsSendChannel(DEFAULT_SMS_SEND_CHANNEL)
            .remark(DEFAULT_REMARK)
            .enable(DEFAULT_ENABLE)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateTime(DEFAULT_UPDATE_TIME);
        return messageTemplate;
    }

    @Before
    public void initTest() {
        messageTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessageTemplate() throws Exception {
        int databaseSizeBeforeCreate = messageTemplateRepository.findAll().size();

        // Create the MessageTemplate
        restMessageTemplateMockMvc.perform(post("/api/message-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageTemplate)))
            .andExpect(status().isCreated());

        // Validate the MessageTemplate in the database
        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAll();
        assertThat(messageTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        MessageTemplate testMessageTemplate = messageTemplateList.get(messageTemplateList.size() - 1);
        assertThat(testMessageTemplate.getOid()).isEqualTo(DEFAULT_OID);
        assertThat(testMessageTemplate.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testMessageTemplate.isParamFlag()).isEqualTo(DEFAULT_PARAM_FLAG);
        assertThat(testMessageTemplate.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMessageTemplate.getSmsSendChannel()).isEqualTo(DEFAULT_SMS_SEND_CHANNEL);
        assertThat(testMessageTemplate.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testMessageTemplate.getEnable()).isEqualTo(DEFAULT_ENABLE);
        assertThat(testMessageTemplate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMessageTemplate.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testMessageTemplate.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testMessageTemplate.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createMessageTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageTemplateRepository.findAll().size();

        // Create the MessageTemplate with an existing ID
        messageTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageTemplateMockMvc.perform(post("/api/message-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the MessageTemplate in the database
        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAll();
        assertThat(messageTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOidIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTemplateRepository.findAll().size();
        // set the field null
        messageTemplate.setOid(null);

        // Create the MessageTemplate, which fails.

        restMessageTemplateMockMvc.perform(post("/api/message-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageTemplate)))
            .andExpect(status().isBadRequest());

        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAll();
        assertThat(messageTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTemplateRepository.findAll().size();
        // set the field null
        messageTemplate.setContent(null);

        // Create the MessageTemplate, which fails.

        restMessageTemplateMockMvc.perform(post("/api/message-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageTemplate)))
            .andExpect(status().isBadRequest());

        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAll();
        assertThat(messageTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParamFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTemplateRepository.findAll().size();
        // set the field null
        messageTemplate.setParamFlag(null);

        // Create the MessageTemplate, which fails.

        restMessageTemplateMockMvc.perform(post("/api/message-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageTemplate)))
            .andExpect(status().isBadRequest());

        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAll();
        assertThat(messageTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTemplateRepository.findAll().size();
        // set the field null
        messageTemplate.setType(null);

        // Create the MessageTemplate, which fails.

        restMessageTemplateMockMvc.perform(post("/api/message-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageTemplate)))
            .andExpect(status().isBadRequest());

        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAll();
        assertThat(messageTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTemplateRepository.findAll().size();
        // set the field null
        messageTemplate.setEnable(null);

        // Create the MessageTemplate, which fails.

        restMessageTemplateMockMvc.perform(post("/api/message-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageTemplate)))
            .andExpect(status().isBadRequest());

        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAll();
        assertThat(messageTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTemplateRepository.findAll().size();
        // set the field null
        messageTemplate.setCreatedBy(null);

        // Create the MessageTemplate, which fails.

        restMessageTemplateMockMvc.perform(post("/api/message-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageTemplate)))
            .andExpect(status().isBadRequest());

        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAll();
        assertThat(messageTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTemplateRepository.findAll().size();
        // set the field null
        messageTemplate.setCreateTime(null);

        // Create the MessageTemplate, which fails.

        restMessageTemplateMockMvc.perform(post("/api/message-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageTemplate)))
            .andExpect(status().isBadRequest());

        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAll();
        assertThat(messageTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTemplateRepository.findAll().size();
        // set the field null
        messageTemplate.setUpdatedBy(null);

        // Create the MessageTemplate, which fails.

        restMessageTemplateMockMvc.perform(post("/api/message-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageTemplate)))
            .andExpect(status().isBadRequest());

        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAll();
        assertThat(messageTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTemplateRepository.findAll().size();
        // set the field null
        messageTemplate.setUpdateTime(null);

        // Create the MessageTemplate, which fails.

        restMessageTemplateMockMvc.perform(post("/api/message-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageTemplate)))
            .andExpect(status().isBadRequest());

        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAll();
        assertThat(messageTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMessageTemplates() throws Exception {
        // Initialize the database
        messageTemplateRepository.saveAndFlush(messageTemplate);

        // Get all the messageTemplateList
        restMessageTemplateMockMvc.perform(get("/api/message-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].oid").value(hasItem(DEFAULT_OID.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].paramFlag").value(hasItem(DEFAULT_PARAM_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].smsSendChannel").value(hasItem(DEFAULT_SMS_SEND_CHANNEL.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getMessageTemplate() throws Exception {
        // Initialize the database
        messageTemplateRepository.saveAndFlush(messageTemplate);

        // Get the messageTemplate
        restMessageTemplateMockMvc.perform(get("/api/message-templates/{id}", messageTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(messageTemplate.getId().intValue()))
            .andExpect(jsonPath("$.oid").value(DEFAULT_OID.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.paramFlag").value(DEFAULT_PARAM_FLAG.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.smsSendChannel").value(DEFAULT_SMS_SEND_CHANNEL.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMessageTemplate() throws Exception {
        // Get the messageTemplate
        restMessageTemplateMockMvc.perform(get("/api/message-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessageTemplate() throws Exception {
        // Initialize the database
        messageTemplateService.save(messageTemplate);

        int databaseSizeBeforeUpdate = messageTemplateRepository.findAll().size();

        // Update the messageTemplate
        MessageTemplate updatedMessageTemplate = messageTemplateRepository.findOne(messageTemplate.getId());
        updatedMessageTemplate
            .oid(UPDATED_OID)
            .content(UPDATED_CONTENT)
            .paramFlag(UPDATED_PARAM_FLAG)
            .type(UPDATED_TYPE)
            .smsSendChannel(UPDATED_SMS_SEND_CHANNEL)
            .remark(UPDATED_REMARK)
            .enable(UPDATED_ENABLE)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateTime(UPDATED_UPDATE_TIME);

        restMessageTemplateMockMvc.perform(put("/api/message-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMessageTemplate)))
            .andExpect(status().isOk());

        // Validate the MessageTemplate in the database
        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAll();
        assertThat(messageTemplateList).hasSize(databaseSizeBeforeUpdate);
        MessageTemplate testMessageTemplate = messageTemplateList.get(messageTemplateList.size() - 1);
        assertThat(testMessageTemplate.getOid()).isEqualTo(UPDATED_OID);
        assertThat(testMessageTemplate.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testMessageTemplate.isParamFlag()).isEqualTo(UPDATED_PARAM_FLAG);
        assertThat(testMessageTemplate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMessageTemplate.getSmsSendChannel()).isEqualTo(UPDATED_SMS_SEND_CHANNEL);
        assertThat(testMessageTemplate.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testMessageTemplate.getEnable()).isEqualTo(UPDATED_ENABLE);
        assertThat(testMessageTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMessageTemplate.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testMessageTemplate.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testMessageTemplate.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingMessageTemplate() throws Exception {
        int databaseSizeBeforeUpdate = messageTemplateRepository.findAll().size();

        // Create the MessageTemplate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMessageTemplateMockMvc.perform(put("/api/message-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageTemplate)))
            .andExpect(status().isCreated());

        // Validate the MessageTemplate in the database
        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAll();
        assertThat(messageTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMessageTemplate() throws Exception {
        // Initialize the database
        messageTemplateService.save(messageTemplate);

        int databaseSizeBeforeDelete = messageTemplateRepository.findAll().size();

        // Get the messageTemplate
        restMessageTemplateMockMvc.perform(delete("/api/message-templates/{id}", messageTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAll();
        assertThat(messageTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageTemplate.class);
        MessageTemplate messageTemplate1 = new MessageTemplate();
        messageTemplate1.setId(1L);
        MessageTemplate messageTemplate2 = new MessageTemplate();
        messageTemplate2.setId(messageTemplate1.getId());
        assertThat(messageTemplate1).isEqualTo(messageTemplate2);
        messageTemplate2.setId(2L);
        assertThat(messageTemplate1).isNotEqualTo(messageTemplate2);
        messageTemplate1.setId(null);
        assertThat(messageTemplate1).isNotEqualTo(messageTemplate2);
    }
}

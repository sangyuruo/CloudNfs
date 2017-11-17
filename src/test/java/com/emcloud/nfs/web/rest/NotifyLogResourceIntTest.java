package com.emcloud.nfs.web.rest;

import com.emcloud.nfs.EmCloudNfsApp;

import com.emcloud.nfs.config.SecurityBeanOverrideConfiguration;

import com.emcloud.nfs.domain.NotifyLog;
import com.emcloud.nfs.repository.NotifyLogRepository;
import com.emcloud.nfs.service.NotifyLogService;
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
 * Test class for the NotifyLogResource REST controller.
 *
 * @see NotifyLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudNfsApp.class, SecurityBeanOverrideConfiguration.class})
public class NotifyLogResourceIntTest {

    private static final Integer DEFAULT_MTID = 1;
    private static final Integer UPDATED_MTID = 2;

    private static final Integer DEFAULT_SEND_TYPE = 1;
    private static final Integer UPDATED_SEND_TYPE = 2;

    private static final String DEFAULT_SEND_TARGET = "AAAAAAAAAA";
    private static final String UPDATED_SEND_TARGET = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Integer DEFAULT_READ_FLAG = 0;
    private static final Integer UPDATED_READ_FLAG = 1;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private NotifyLogRepository notifyLogRepository;

    @Autowired
    private NotifyLogService notifyLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNotifyLogMockMvc;

    private NotifyLog notifyLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotifyLogResource notifyLogResource = new NotifyLogResource(notifyLogService);
        this.restNotifyLogMockMvc = MockMvcBuilders.standaloneSetup(notifyLogResource)
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
    public static NotifyLog createEntity(EntityManager em) {
        NotifyLog notifyLog = new NotifyLog()
            .mtid(DEFAULT_MTID)
            .sendType(DEFAULT_SEND_TYPE)
            .sendTarget(DEFAULT_SEND_TARGET)
            .content(DEFAULT_CONTENT)
            .status(DEFAULT_STATUS)
            .readFlag(DEFAULT_READ_FLAG)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return notifyLog;
    }

    @Before
    public void initTest() {
        notifyLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotifyLog() throws Exception {
        int databaseSizeBeforeCreate = notifyLogRepository.findAll().size();

        // Create the NotifyLog
        restNotifyLogMockMvc.perform(post("/api/notify-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyLog)))
            .andExpect(status().isCreated());

        // Validate the NotifyLog in the database
        List<NotifyLog> notifyLogList = notifyLogRepository.findAll();
        assertThat(notifyLogList).hasSize(databaseSizeBeforeCreate + 1);
        NotifyLog testNotifyLog = notifyLogList.get(notifyLogList.size() - 1);
        assertThat(testNotifyLog.getMtid()).isEqualTo(DEFAULT_MTID);
        assertThat(testNotifyLog.getSendType()).isEqualTo(DEFAULT_SEND_TYPE);
        assertThat(testNotifyLog.getSendTarget()).isEqualTo(DEFAULT_SEND_TARGET);
        assertThat(testNotifyLog.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testNotifyLog.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNotifyLog.isReadFlag()).isEqualTo(DEFAULT_READ_FLAG);
        assertThat(testNotifyLog.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testNotifyLog.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testNotifyLog.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createNotifyLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notifyLogRepository.findAll().size();

        // Create the NotifyLog with an existing ID
        notifyLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotifyLogMockMvc.perform(post("/api/notify-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyLog)))
            .andExpect(status().isBadRequest());

        // Validate the NotifyLog in the database
        List<NotifyLog> notifyLogList = notifyLogRepository.findAll();
        assertThat(notifyLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMtidIsRequired() throws Exception {
        int databaseSizeBeforeTest = notifyLogRepository.findAll().size();
        // set the field null
        notifyLog.setMtid(null);

        // Create the NotifyLog, which fails.

        restNotifyLogMockMvc.perform(post("/api/notify-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyLog)))
            .andExpect(status().isBadRequest());

        List<NotifyLog> notifyLogList = notifyLogRepository.findAll();
        assertThat(notifyLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSendTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = notifyLogRepository.findAll().size();
        // set the field null
        notifyLog.setSendType(null);

        // Create the NotifyLog, which fails.

        restNotifyLogMockMvc.perform(post("/api/notify-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyLog)))
            .andExpect(status().isBadRequest());

        List<NotifyLog> notifyLogList = notifyLogRepository.findAll();
        assertThat(notifyLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSendTargetIsRequired() throws Exception {
        int databaseSizeBeforeTest = notifyLogRepository.findAll().size();
        // set the field null
        notifyLog.setSendTarget(null);

        // Create the NotifyLog, which fails.

        restNotifyLogMockMvc.perform(post("/api/notify-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyLog)))
            .andExpect(status().isBadRequest());

        List<NotifyLog> notifyLogList = notifyLogRepository.findAll();
        assertThat(notifyLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = notifyLogRepository.findAll().size();
        // set the field null
        notifyLog.setContent(null);

        // Create the NotifyLog, which fails.

        restNotifyLogMockMvc.perform(post("/api/notify-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyLog)))
            .andExpect(status().isBadRequest());

        List<NotifyLog> notifyLogList = notifyLogRepository.findAll();
        assertThat(notifyLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = notifyLogRepository.findAll().size();
        // set the field null
        notifyLog.setStatus(null);

        // Create the NotifyLog, which fails.

        restNotifyLogMockMvc.perform(post("/api/notify-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyLog)))
            .andExpect(status().isBadRequest());

        List<NotifyLog> notifyLogList = notifyLogRepository.findAll();
        assertThat(notifyLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReadFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = notifyLogRepository.findAll().size();
        // set the field null
        notifyLog.setReadFlag(null);

        // Create the NotifyLog, which fails.

        restNotifyLogMockMvc.perform(post("/api/notify-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyLog)))
            .andExpect(status().isBadRequest());

        List<NotifyLog> notifyLogList = notifyLogRepository.findAll();
        assertThat(notifyLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = notifyLogRepository.findAll().size();
        // set the field null
        notifyLog.setCreatedBy(null);

        // Create the NotifyLog, which fails.

        restNotifyLogMockMvc.perform(post("/api/notify-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyLog)))
            .andExpect(status().isBadRequest());

        List<NotifyLog> notifyLogList = notifyLogRepository.findAll();
        assertThat(notifyLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = notifyLogRepository.findAll().size();
        // set the field null
        notifyLog.setCreateTime(null);

        // Create the NotifyLog, which fails.

        restNotifyLogMockMvc.perform(post("/api/notify-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyLog)))
            .andExpect(status().isBadRequest());

        List<NotifyLog> notifyLogList = notifyLogRepository.findAll();
        assertThat(notifyLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = notifyLogRepository.findAll().size();
        // set the field null
        notifyLog.setUpdateTime(null);

        // Create the NotifyLog, which fails.

        restNotifyLogMockMvc.perform(post("/api/notify-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyLog)))
            .andExpect(status().isBadRequest());

        List<NotifyLog> notifyLogList = notifyLogRepository.findAll();
        assertThat(notifyLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotifyLogs() throws Exception {
        // Initialize the database
        notifyLogRepository.saveAndFlush(notifyLog);

        // Get all the notifyLogList
        restNotifyLogMockMvc.perform(get("/api/notify-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notifyLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].mtid").value(hasItem(DEFAULT_MTID)))
            .andExpect(jsonPath("$.[*].sendType").value(hasItem(DEFAULT_SEND_TYPE)))
            .andExpect(jsonPath("$.[*].sendTarget").value(hasItem(DEFAULT_SEND_TARGET.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].readFlag").value(hasItem(DEFAULT_READ_FLAG)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getNotifyLog() throws Exception {
        // Initialize the database
        notifyLogRepository.saveAndFlush(notifyLog);

        // Get the notifyLog
        restNotifyLogMockMvc.perform(get("/api/notify-logs/{id}", notifyLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(notifyLog.getId().intValue()))
            .andExpect(jsonPath("$.mtid").value(DEFAULT_MTID))
            .andExpect(jsonPath("$.sendType").value(DEFAULT_SEND_TYPE))
            .andExpect(jsonPath("$.sendTarget").value(DEFAULT_SEND_TARGET.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.readFlag").value(DEFAULT_READ_FLAG))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNotifyLog() throws Exception {
        // Get the notifyLog
        restNotifyLogMockMvc.perform(get("/api/notify-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotifyLog() throws Exception {
        // Initialize the database
        notifyLogService.save(notifyLog);

        int databaseSizeBeforeUpdate = notifyLogRepository.findAll().size();

        // Update the notifyLog
        NotifyLog updatedNotifyLog = notifyLogRepository.findOne(notifyLog.getId());
        updatedNotifyLog
            .mtid(UPDATED_MTID)
            .sendType(UPDATED_SEND_TYPE)
            .sendTarget(UPDATED_SEND_TARGET)
            .content(UPDATED_CONTENT)
            .status(UPDATED_STATUS)
            .readFlag(UPDATED_READ_FLAG)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restNotifyLogMockMvc.perform(put("/api/notify-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotifyLog)))
            .andExpect(status().isOk());

        // Validate the NotifyLog in the database
        List<NotifyLog> notifyLogList = notifyLogRepository.findAll();
        assertThat(notifyLogList).hasSize(databaseSizeBeforeUpdate);
        NotifyLog testNotifyLog = notifyLogList.get(notifyLogList.size() - 1);
        assertThat(testNotifyLog.getMtid()).isEqualTo(UPDATED_MTID);
        assertThat(testNotifyLog.getSendType()).isEqualTo(UPDATED_SEND_TYPE);
        assertThat(testNotifyLog.getSendTarget()).isEqualTo(UPDATED_SEND_TARGET);
        assertThat(testNotifyLog.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testNotifyLog.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNotifyLog.isReadFlag()).isEqualTo(UPDATED_READ_FLAG);
        assertThat(testNotifyLog.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNotifyLog.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testNotifyLog.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingNotifyLog() throws Exception {
        int databaseSizeBeforeUpdate = notifyLogRepository.findAll().size();

        // Create the NotifyLog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNotifyLogMockMvc.perform(put("/api/notify-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyLog)))
            .andExpect(status().isCreated());

        // Validate the NotifyLog in the database
        List<NotifyLog> notifyLogList = notifyLogRepository.findAll();
        assertThat(notifyLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNotifyLog() throws Exception {
        // Initialize the database
        notifyLogService.save(notifyLog);

        int databaseSizeBeforeDelete = notifyLogRepository.findAll().size();

        // Get the notifyLog
        restNotifyLogMockMvc.perform(delete("/api/notify-logs/{id}", notifyLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NotifyLog> notifyLogList = notifyLogRepository.findAll();
        assertThat(notifyLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotifyLog.class);
        NotifyLog notifyLog1 = new NotifyLog();
        notifyLog1.setId(1L);
        NotifyLog notifyLog2 = new NotifyLog();
        notifyLog2.setId(notifyLog1.getId());
        assertThat(notifyLog1).isEqualTo(notifyLog2);
        notifyLog2.setId(2L);
        assertThat(notifyLog1).isNotEqualTo(notifyLog2);
        notifyLog1.setId(null);
        assertThat(notifyLog1).isNotEqualTo(notifyLog2);
    }
}

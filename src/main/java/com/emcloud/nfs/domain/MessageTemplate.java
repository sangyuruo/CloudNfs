package com.emcloud.nfs.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 消息模板表
 * @author daiziying
 */
@ApiModel(description = "消息模板表 @author daiziying")
@Entity
@Table(name = "message_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MessageTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 业务主键
     */
    @NotNull
    @ApiModelProperty(value = "业务主键", required = true)
    @Column(name = "mt_code", nullable = false)
    private String mtCode;

    /**
     * 内容
     */
    @NotNull
    @Size(max = 200)
    @ApiModelProperty(value = "内容", required = true)
    @Column(name = "content", length = 200, nullable = false)
    private String content;

    /**
     * 是否包含参数：1，包含；0，不包含
     */
    @NotNull
    @ApiModelProperty(value = "是否包含参数：1，包含；0，不包含", required = true)
    @Column(name = "param_flag", nullable = false)
    private Boolean paramFlag;

    /**
     * 类型：1短信；2邮件；3app通知
     */
    @NotNull
    @ApiModelProperty(value = "类型：1短信；2邮件；3app通知", required = true)
    @Column(name = "jhi_type", nullable = false)
    private Integer type;

    /**
     * 消息发送渠道
     */
    @Size(max = 50)
    @ApiModelProperty(value = "消息发送渠道")
    @Column(name = "sms_send_channel", length = 50)
    private String smsSendChannel;

    /**
     * 备注
     */
    @Size(max = 500)
    @ApiModelProperty(value = "备注")
    @Column(name = "remark", length = 500)
    private String remark;

    /**
     * 是否可用
     */
    @NotNull
    @ApiModelProperty(value = "是否可用", required = true)
    @Column(name = "jhi_enable", nullable = false)
    private Integer enable;

    /**
     * 创建人
     */
    @Size(max = 20)
    @ApiModelProperty(value = "创建人", required = true)
    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    /**
     * 修改人
     */
    @Size(max = 20)
    @ApiModelProperty(value = "修改人", required = true)
    @Column(name = "updated_by", length = 20, nullable = false)
    private String updatedBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", required = true)
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOid() {
        return mtCode;
    }

    public MessageTemplate oid(String oid) {
        this.mtCode = oid;
        return this;
    }

    public void setOid(String oid) {
        this.mtCode = oid;
    }

    public String getContent() {
        return content;
    }

    public MessageTemplate content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isParamFlag() {
        return paramFlag;
    }

    public MessageTemplate paramFlag(Boolean paramFlag) {
        this.paramFlag = paramFlag;
        return this;
    }

    public void setParamFlag(Boolean paramFlag) {
        this.paramFlag = paramFlag;
    }

    public Integer getType() {
        return type;
    }

    public MessageTemplate type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSmsSendChannel() {
        return smsSendChannel;
    }

    public MessageTemplate smsSendChannel(String smsSendChannel) {
        this.smsSendChannel = smsSendChannel;
        return this;
    }

    public void setSmsSendChannel(String smsSendChannel) {
        this.smsSendChannel = smsSendChannel;
    }

    public String getRemark() {
        return remark;
    }

    public MessageTemplate remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getEnable() {
        return enable;
    }

    public MessageTemplate enable(Integer enable) {
        this.enable = enable;
        return this;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public MessageTemplate createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public MessageTemplate createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public MessageTemplate updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public MessageTemplate updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageTemplate messageTemplate = (MessageTemplate) o;
        if (messageTemplate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messageTemplate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MessageTemplate{" +
            "id=" + getId() +
            ", oid='" + getOid() + "'" +
            ", content='" + getContent() + "'" +
            ", paramFlag='" + isParamFlag() + "'" +
            ", type='" + getType() + "'" +
            ", smsSendChannel='" + getSmsSendChannel() + "'" +
            ", remark='" + getRemark() + "'" +
            ", enable='" + getEnable() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}

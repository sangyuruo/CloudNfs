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
 * 通知记录表
 * @author daiziying
 */
@ApiModel(description = "通知记录表 @author daiziying")
@Entity
@Table(name = "notify_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NotifyLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 通用类短信配置表id
     */
    @NotNull
    @ApiModelProperty(value = "通用类短信配置表id", required = true)
    @Column(name = "mtid", nullable = false)
    private Integer mtid;

    /**
     * 发送类型 类型：1短信；2邮件；3app通知
     */
    @NotNull
    @ApiModelProperty(value = "发送类型 类型：1短信；2邮件；3app通知", required = true)
    @Column(name = "send_type", nullable = false)
    private Integer sendType;

    /**
     * 发送目标
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "发送目标", required = true)
    @Column(name = "send_target", length = 50, nullable = false)
    private String sendTarget;

    /**
     * 内容
     */
    @NotNull
    @Size(max = 200)
    @ApiModelProperty(value = "内容", required = true)
    @Column(name = "content", length = 200, nullable = false)
    private String content;

    /**
     * 状态 1--成功，0--失败
     */
    @NotNull
    @ApiModelProperty(value = "状态 1--成功，0--失败", required = true)
    @Column(name = "status", nullable = false)
    private Boolean status;

    /**
     * 是否已读， 1--已读，0--未读
     */
    @NotNull
    @ApiModelProperty(value = "是否已读， 1--已读，0--未读", required = true)
    @Column(name = "read_flag", nullable = false)
    private Boolean readFlag;

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

    public Integer getMtid() {
        return mtid;
    }

    public NotifyLog mtid(Integer mtid) {
        this.mtid = mtid;
        return this;
    }

    public void setMtid(Integer mtid) {
        this.mtid = mtid;
    }

    public Integer getSendType() {
        return sendType;
    }

    public NotifyLog sendType(Integer sendType) {
        this.sendType = sendType;
        return this;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public String getSendTarget() {
        return sendTarget;
    }

    public NotifyLog sendTarget(String sendTarget) {
        this.sendTarget = sendTarget;
        return this;
    }

    public void setSendTarget(String sendTarget) {
        this.sendTarget = sendTarget;
    }

    public String getContent() {
        return content;
    }

    public NotifyLog content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isStatus() {
        return status;
    }

    public NotifyLog status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isReadFlag() {
        return readFlag;
    }

    public NotifyLog readFlag(Boolean readFlag) {
        this.readFlag = readFlag;
        return this;
    }

    public void setReadFlag(Boolean readFlag) {
        this.readFlag = readFlag;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public NotifyLog createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public NotifyLog createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public NotifyLog updateTime(Instant updateTime) {
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
        NotifyLog notifyLog = (NotifyLog) o;
        if (notifyLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notifyLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NotifyLog{" +
            "id=" + getId() +
            ", mtid='" + getMtid() + "'" +
            ", sendType='" + getSendType() + "'" +
            ", sendTarget='" + getSendTarget() + "'" +
            ", content='" + getContent() + "'" +
            ", status='" + isStatus() + "'" +
            ", readFlag='" + isReadFlag() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}

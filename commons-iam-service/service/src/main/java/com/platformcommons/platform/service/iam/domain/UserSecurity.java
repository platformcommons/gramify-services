package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user", "tenant_id"})})
public class UserSecurity extends BaseTransactionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "password")
    private String password;

    @Column(name = "password_set_on")
    private Date passwordSetOn;

    @Column(name = "is_expired")
    private Boolean isExpired;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    @Column(name = "is_account_locked")
    private Boolean isAccountLocked;

    @Column(name = "account_lock_reason")
    private String accountLockReason;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_key")
    private String otpKey;

    @Column(name = "otp_for")
    private String otpFor;

    @Column(name = "otp_validity")
    private Date otpValidity;

    @Column(name = "otp_generate_at")
    private Long otpGeneratedAt;

    @Column(name = "failed_otp_verification")
    private Integer failedOTPVerification;


    @Builder
    public UserSecurity(String uuid, Long tenantId, Boolean isActive, String inactiveReason, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, User user, String password, Date passwordSetOn, Boolean isExpired, Integer failedLoginAttempts, Boolean isAccountLocked, String accountLockReason, String otp, String otpKey, String otpFor, Date otpValidity, Long otpGeneratedAt, Integer failedOTPVerification) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.user = user;
        this.password = password;
        this.passwordSetOn = passwordSetOn;
        this.isExpired = isExpired;
        this.failedLoginAttempts = failedLoginAttempts;
        this.isAccountLocked = isAccountLocked;
        this.accountLockReason = accountLockReason;
        this.otp = otp;
        this.otpKey = otpKey;
        this.otpFor = otpFor;
        this.otpValidity = otpValidity;
        this.otpGeneratedAt = otpGeneratedAt;
        this.failedOTPVerification = failedOTPVerification;
    }

    public void init() {
        this.id = 0L;
        this.passwordSetOn = new Date();
        this.failedLoginAttempts=0;
        this.isExpired=false;
    }

    public void updateLoginAttempts() {
        if(failedLoginAttempts==null){
            failedLoginAttempts=0;
        }
        this.failedLoginAttempts++;
    }

    public void lockAccount(String reason) {
        this.isAccountLocked = true;
        this.accountLockReason=reason;
    }

    public void resetFailedLoginAttempts() {
        this.failedLoginAttempts=0;
    }
    public void resetFailedOTPVerification() {
        this.failedOTPVerification = 0;
    }

    public void resetOtp() {
        this.otp=null;
        this.otpKey=null;
        this.otpValidity=null;
        this.otpFor=null;
    }

    public void generateOtp(String otp, String otpKey, Date otpValidity,String otpFor) {
        this.otp=otp;
        this.otpKey=otpKey;
        this.otpValidity=otpValidity;
        this.otpFor=otpFor;
        this.otpGeneratedAt=Instant.now().toEpochMilli();
    }

    public void unlock() {
        this.isAccountLocked=false;
        this.accountLockReason=null;
    }

    public boolean checkOTPExpired() {
        return new Date(System.currentTimeMillis()).after(otpValidity);
    }

    public void updateFailedOTPVerification() {
        if(this.failedOTPVerification==null) this.failedOTPVerification=0;
        this.failedOTPVerification++;
    }
}

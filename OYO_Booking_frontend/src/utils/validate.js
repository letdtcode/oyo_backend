import { el } from 'date-fns/locale';
import { t } from 'i18next';

export const validateEmail = (email) => {
    const errors = {};
    if (!email) {
        errors.email = 'Nhập email của bạn';
    } else if (!/\S+@\S+\.\S+/.test(email)) {
        errors.email = t('validate.emailError');
    } else {
        delete errors.email;
    }
    return errors;
};
export const validateChangePassword = (data) => {
    const errors = {};
    if (data.newPassword) {
        if (data.newPassword.length < 8) {
            errors.newPassword = t('validate.passwordMinError');
        } else if (!data.newPassword.match(/[A-Z]/)) {
            errors.newPassword = t('validate.passwordUpperCaseError');
        } else if (!data.newPassword.match(/[a-z]/)) {
            errors.newPassword = t('validate.passwordLowerCaseError');
        } else if (!data.newPassword.match(/[0-9]/)) {
            errors.newPassword = t('validate.passwordNumberError');
        } else if (!data.newPassword.match(/[!@#$%^&?*]/)) {
            errors.newPassword = t('validate.passwordSpecialCharError');
        }
    } else {
        errors.newPassword = t('validate.passwordRequire');
    }
    if (data.enterNewPassword) {
        if (data.enterNewPassword !== data.newPassword) {
            errors.enterNewPassword = t('validate.passwordConfirmError');
        }
    } else {
        errors.enterNewPassword = t('validate.passwordRequire');
    }
    if (data.oldPassword) {
        if (data.oldPassword.length < 8) {
            errors.oldPassword = t('validate.passwordMinError');
        }
    } else {
        errors.oldPassword = t('validate.passwordRequire');
    }
    return errors;
};
export const validate = (data) => {
    const errors = {};

    if (data.firstName && data.firstName.trim()) {
    } else {
        errors.firstName = 'Không thể bỏ trống';
    }

    if (data.lastName && data.lastName.trim()) {
    } else {
        errors.lastName = 'Không thể bỏ trống';
    }

    if (data.email) {
        if (!/\S+@\S+\.\S+/.test(data.email)) {
            errors.email = t('validate.emailError');
        }
    } else {
        errors.email = 'Nhập email của bạn';
    }

    // Validate Password
    if (data.password) {
        if (data.password.length < 8) {
            errors.password = t('validate.passwordMinError');
        } else if (!data.password.match(/[A-Z]/)) {
            errors.password = t('validate.passwordUpperCaseError');
        } else if (!data.password.match(/[a-z]/)) {
            errors.password = t('validate.passwordLowerCaseError');
        } else if (!data.password.match(/[0-9]/)) {
            errors.password = t('validate.passwordNumberError');
        } else if (!data.password.match(/[!@#$%^&?*]/)) {
            errors.password = t('validate.passwordSpecialCharError');
        }
    } else {
        errors.password = t('validate.passwordRequire');
    }
    return errors;
};

export const validateInfo = (data) => {
    const errors = {};
    if (data.userName && data.userName.trim()) {
        if (data.userName.length > 15) {
            errors.userName = t('validate.maxCharacter');
        }
    } else {
        errors.username = 'Không thể bỏ trống';
    }
    if (data.phone && data.phone.trim()) {
        if (data.phone.length !== 10 || !data.phone.match(/^[0-9]+$/)) {
            errors.phone = 'Số điện thoại không hợp lệ';
        }
    } else {
        errors.phone = 'Không thể bỏ trống';
    }

    if (data.firstName && data.firstName.trim()) {
    } else {
        errors.firstName = 'Không thể bỏ trống';
    }

    if (data.lastName && data.lastName.trim()) {
    } else {
        errors.lastName = 'Không thể bỏ trống';
    }
    if (data.address && data.address.trim()) {
    } else {
        errors.address = 'Không thể bỏ trống';
    }

    return errors;
};

export const validateBooking = (data) => {
    const errors = {};
    if (data.phoneNumberCustomer && data.phoneNumberCustomer.trim()) {
        if (data.phoneNumberCustomer.length !== 10 || !data.phoneNumberCustomer.match(/^[0-9]+$/)) {
            errors.phoneNumberCustomer = t('validate.phoneError');
        }
    } else {
        errors.phoneNumberCustomer = t('validate.phoneRequire');
    }
    return errors;
};

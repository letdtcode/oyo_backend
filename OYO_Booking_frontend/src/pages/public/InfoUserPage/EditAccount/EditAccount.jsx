import React, { useEffect, useState } from 'react';
import MenuItem from '@mui/material/MenuItem';
import { Grid } from '@mui/material';
import FormControl from '@mui/material/FormControl';
import Button from '@mui/material/Button';
import CustomInput from '~/assets/custom/CustomInput';
import { useDispatch, useSelector } from 'react-redux';
import userSlice from '~/redux/userSlice';
import authAPI from '~/services/apis/authAPI/authAPI';
import { useSnackbar } from 'notistack';
import { format, parse } from 'date-fns';
import { validateInfo } from '~/utils/validate';
import { t } from 'i18next';
import { da } from 'date-fns/locale';
import { clear } from 'i/lib/inflections';

export default function EditAccount() {
    const { enqueueSnackbar } = useSnackbar();
    const [submit, setSubmit] = useState(false);
    const userCurrent = useSelector((state) => state.user.current);
    const [user, setUser] = useState(userCurrent);
    const [errors, setErrors] = useState({});
    const dispatch = useDispatch();
    useEffect(() => {
        if (user && user.dateOfBirth) {
            const updatedUser = { ...user };
            const parsedDate = parse(user.dateOfBirth, 'dd/MM/yyyy', new Date());
            updatedUser.birthday = format(parsedDate, 'dd');
            updatedUser.monthOfBirth = format(parsedDate, 'MM');
            updatedUser.yearOfBirth = format(parsedDate, 'yyyy');
            setUser(updatedUser);
        }
    }, [userCurrent]);
    useEffect(() => {
        if (user.birthday && user.monthOfBirth && user.yearOfBirth) {
            const dateOfBirth = Date.parse(
                `${user.yearOfBirth}-${user.monthOfBirth}-${user.birthday}`,
                'yyyy-MM-dd',
                new Date()
            );

            setUser({
                ...user,
                dateOfBirth: format(dateOfBirth, 'dd/MM/yyyy')
            });
        }
    }, [user.birthday, user.monthOfBirth, user.yearOfBirth]);
    const handleUser = (event) => {
        setUser({ ...user, [event.target.name]: event.target.value });
        setSubmit(true);
    };

    const handleSave = async (event) => {
        event.preventDefault();
        const check = validateInfo(user);
        if (Object.keys(check).length === 0) {
            const res = await authAPI.updateInfoRequest(user);
            if (res.statusCode === 200) {
                dispatch(userSlice.actions.editInfo(res.data));
                enqueueSnackbar(t('message.updateSuccess'), { variant: 'success' });
            } else {
                enqueueSnackbar('Cập nhật thất bại', { variant: 'error' });
            }
        } else {
            setErrors(check);
        }
    };
    const genderSelect = [
        { value: 2, label: t('label.male') },
        { value: 1, label: t('label.female') }
    ];
    const birthday = [];
    for (let i = 1; i <= 31; i++) {
        const formattedValue = i.toString().padStart(2, '0');
        birthday.push({ id: i, value: formattedValue, label: formattedValue });
    }

    const monthOfBirth = [];
    for (let i = 1; i <= 12; i++) {
        const formattedValue = i.toString().padStart(2, '0');
        monthOfBirth.push({ id: i, value: formattedValue, label: formattedValue });
    }

    const yearOfBirth = [];
    for (let i = 2023; i > 1960; i--) {
        yearOfBirth.push({ id: i, value: i, label: i.toString() });
    }
    const customInputList = [
        createCustomInput(errors.userName, 6, 'userName', user?.userName || '', handleUser),
        createCustomInput(errors.phone, 6, 'phone', user?.phone || '', handleUser),
        createCustomInput(errors.firstName, 6, 'firstName', user?.firstName || '', handleUser),
        createCustomInput(errors.lastName, 6, 'lastName', user?.lastName || '', handleUser),
        createCustomInput(errors.address, 12, 'address', user?.address || '', handleUser),
        createCustomInput(
            errors.gender,
            3,
            'gender',
            user.gender || '',
            handleUser,
            true,
            genderSelect.map((option) => (
                <MenuItem key={option.value} value={option.value}>
                    {option.label}
                </MenuItem>
            ))
        ),
        createCustomInput(
            errors.birthday,
            3,
            'birthday',
            user.birthday || '',
            handleUser,
            true,
            birthday.map((option) => (
                <MenuItem key={option.id} value={option.value}>
                    {option.label}
                </MenuItem>
            ))
        ),
        createCustomInput(
            errors.monthOfBirth,
            3,
            'monthOfBirth',
            user.monthOfBirth || '',
            handleUser,
            true,
            monthOfBirth.map((option) => (
                <MenuItem key={option.id} value={option.value}>
                    {option.label}
                </MenuItem>
            ))
        ),
        createCustomInput(
            errors.yearOfBirth,
            3,
            'yearOfBirth',
            user.yearOfBirth || '',
            handleUser,
            true,
            yearOfBirth.map((option) => (
                <MenuItem key={option.id} value={option.value}>
                    {option.label}
                </MenuItem>
            ))
        )
    ];
    return (
        <div className="paper editaccount">
            <h2>{t('navbar.personalData')}</h2>
            <hr className="divider" />
            <FormControl className="form" component="form" onSubmit={handleSave}>
                <Grid container direction={{ xs: 'column', md: 'row' }} columnSpacing={7} rowSpacing={1}>
                    {customInputList.map((customInput, index) => (
                        <Grid item xs={customInput.props.xs} key={index}>
                            {customInput}
                        </Grid>
                    ))}
                    <Grid
                        container
                        justifyContent={{ xs: 'center', md: 'flex-end' }}
                        item
                        xs={12}
                        className="form-button"
                    >
                        <Button className="frame-button save" type="submit" variant="contained" disabled={!submit}>
                            {t('common.save')}
                        </Button>
                    </Grid>
                </Grid>
            </FormControl>
        </div>
    );
}
function createCustomInput(error, xs, name, value, onChange, select = false, content = []) {
    return (
        <CustomInput
            id={name}
            name={name}
            size="small"
            value={value}
            title={t(`label.${name}`)}
            onChange={onChange}
            select={select}
            content={content}
            className={error ? `elementerror` : `element${name}`}
            xs={xs}
        />
    );
}

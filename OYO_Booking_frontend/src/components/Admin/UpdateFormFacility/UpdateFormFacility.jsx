import { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';

import { useForm } from 'react-hook-form';

import './UpdateFormFacility.scss';

const renderItem = ({ item, dataFacilityCategory, setValue, register }) => {
    const handleChangeFaciCateName = (e) => {
        const faciCateName = e.target.value;
        const faciCateCode =
            dataFacilityCategory.find((item) => item.faciCateName === faciCateName)?.faciCateCode ||
            'Mã loại tiện ích tương ứng';
        setValue('facilityCateCode', faciCateCode);
    };

    switch (item.nameRegister) {
        case 'status':
            return (
                <select
                    {...register(item.nameRegister, { required: item.nameRequire })}
                    className="update-form__select"
                >
                    <option value="">Chọn trạng thái</option>
                    <option value="ENABLE">ENABLE</option>
                    <option value="DISABLE">DISABLE</option>
                </select>
            );
        case 'facilityCateName':
            return (
                <select
                    {...register(item.nameRegister, {
                        required: item.nameRequire,
                        onChange: (e) => handleChangeFaciCateName(e)
                    })}
                    className="update-form__select"
                >
                    <option value="">Chọn loại tiện ích</option>
                    {dataFacilityCategory.map((itemFaciCate, index) => (
                        <option value={itemFaciCate.faciCateName} key={index}>
                            {itemFaciCate.faciCateName}
                        </option>
                    ))}
                </select>
            );
        case 'facilityCateCode':
            return (
                <input
                    type="text"
                    className="update-form__select"
                    {...register(item.nameRegister)}
                    disabled
                    placeholder={item.placeholder}
                />
            );
        default:
            return (
                <input
                    type="text"
                    className="update-form__input"
                    placeholder={item.placeholder}
                    {...register(item.nameRegister, {
                        required: item.nameRequire
                    })}
                />
            );
    }
};

export default function UpdateFormFacility(props) {
    const [open, setOpen] = useState(false);

    const {
        register,
        handleSubmit,
        setValue,
        formState: { errors }
    } = useForm();

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    useEffect(() => {
        for (let i = 0; i < props.fieldData.length; i++) {
            setValue(props.fieldData[i].nameRegister, props.data[props.fieldData[i].nameRegister]);
        }
    }, [setValue, props.data, props.fieldData]);

    const onSubmit = async (data) => {
        if (props.updateData) {
            props.updateData(data, props.data.id);
        }
        handleClose();
    };

    return (
        <div>
            <img
                width="24"
                height="24"
                src="https://img.icons8.com/pastel-glyph/25/FAB005/create-new--v3.png"
                className="icon__btn"
                alt="create-new--v3"
                onClick={handleClickOpen}
            />
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title" sx={{ paddingBottom: 0, fontSize: '20px', marginLeft: '10px' }}>
                    {'Cập nhật thông tin'}
                </DialogTitle>
                <form className="dialog__update__facility" onSubmit={handleSubmit(onSubmit)}>
                    <DialogContent>
                        <div className="row">
                            {props.fieldData?.map((item, index) => (
                                <div className="col l-12 key-col" key={index}>
                                    <h2 className="title-field">{item.title}</h2>
                                    {renderItem({
                                        item,
                                        dataFacilityCategory: props.dataFacilityCategory,
                                        register,
                                        setValue,
                                        errors
                                    })}
                                    {errors[item.nameRegister] && (
                                        <span className="message_error">{`${
                                            errors[item.nameRegister] && errors[item.nameRegister]?.message
                                        }`}</span>
                                    )}
                                </div>
                            ))}
                        </div>
                    </DialogContent>
                    <DialogActions sx={{ padding: 3 }}>
                        <Button onClick={handleClose} variant="outlined" color="error">
                            Cancel
                        </Button>
                        <Button type="submit" variant="contained" color="success">
                            Update
                        </Button>
                    </DialogActions>
                </form>
            </Dialog>
        </div>
    );
}

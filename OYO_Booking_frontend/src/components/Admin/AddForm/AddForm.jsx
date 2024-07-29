import './AddForm.scss';

import { useForm } from 'react-hook-form';

const AddForm = ({ addDataNew, fieldData }) => {
    const {
        register,
        reset,
        handleSubmit,
        formState: { errors }
    } = useForm();

    const onSubmit = (data) => {
        if (addDataNew) {
            addDataNew(data);
            reset();
        }
    };

    return (
        <div className="add-form">
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="row">
                    {fieldData?.map((item, index) => (
                        <div className="col l-6 key-col" key={index}>
                            <h2 className="title-field">{item.title}</h2>
                            {item.nameRegister === 'status' ? (
                                <select
                                    {...register(item.nameRegister, { required: item.nameRequire })}
                                    className="add-form__select"
                                >
                                    <option value="">Chọn trạng thái</option>
                                    <option value="ENABLE">ENABLE</option>
                                    <option value="DISABLE">DISABLE</option>
                                </select>
                            ) : (
                                <input
                                    type="text"
                                    className="add-form__input"
                                    placeholder={item.placeholder}
                                    {...register(item.nameRegister, {
                                        required: item.nameRequire
                                    })}
                                />
                            )}

                            {errors[item.nameRegister] && (
                                <span className="message_error">{`${
                                    errors[item.nameRegister] && errors[item.nameRegister]?.message
                                }`}</span>
                            )}
                        </div>
                    ))}
                </div>
                <button type="submit" className="btn-save_newdata">
                    Thêm mới
                </button>
            </form>
        </div>
    );
};

export default AddForm;

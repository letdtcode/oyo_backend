import { styled } from '@mui/material/styles';
import RadioGroup, { useRadioGroup } from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Radio from '@mui/material/Radio';
import { useDispatch } from 'react-redux';

import bookingSlice from '~/redux/bookingSlice';
import './CheckBoxPayment.scss';
import { t } from 'i18next';

const StyledFormControlLabel = styled((props) => <FormControlLabel {...props} />)(({ theme, checked }) => ({
    '.MuiFormControlLabel-label': checked && {
        color: theme.palette.primary.main
    }
}));

function MyFormControlLabel(props) {
    const radioGroup = useRadioGroup();

    let checked = false;

    if (radioGroup) {
        checked = radioGroup.value === props.value;
    }

    return <StyledFormControlLabel checked={checked} {...props} />;
}

export default function CheckBoxPaymentPolicy(props) {
    const dispatch = useDispatch();

    const handleChangeRadio = (event) => {
        dispatch(bookingSlice.actions.addPaymentPolicy(event.target.value));
    };

    return (
        <div className="payment-radio-box">
            <RadioGroup name="use-radio-group" defaultValue="PAYMENT_FULL">
                <MyFormControlLabel
                    value="PAYMENT_FULL"
                    label={t('title.bookingOfYou.payfull')}
                    control={<Radio sx={{ fontSize: '14px' }} onChange={handleChangeRadio} />}
                />
                <MyFormControlLabel
                    value="PAYMENT_HALF"
                    label={t('title.bookingOfYou.pay50')}
                    control={<Radio onChange={handleChangeRadio} />}
                />
            </RadioGroup>
        </div>
    );
}

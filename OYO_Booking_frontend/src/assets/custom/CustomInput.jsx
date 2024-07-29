import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';

export default function CustomInput({
    className,
    width,
    id,
    title,
    fullWidth,
    size = 'medium',
    name,
    value,
    onChange,
    disabled,
    type,
    InputProps,
    select,
    content
}) {
    return (
        <Box className={className} width={width}>
            <label htmlFor={id}>{title}</label>
            <TextField
                className="customInput"
                fullWidth={fullWidth ? false : true}
                margin="dense"
                size={size}
                name={name}
                value={value}
                onChange={onChange}
                disabled={disabled}
                type={type}
                InputProps={InputProps}
                select={select}
            >
                {content}
            </TextField>
        </Box>
    );
}

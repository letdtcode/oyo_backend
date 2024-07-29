import { useEffect, useState } from 'react';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

export default function SelectedIdRoom(props) {
    const [idRoom, setIdRoom] = useState(props.idFirst ? props?.idFirst : '');
    useEffect(() => {
        if (props.handleChangeIdRoom) {
            props.handleChangeIdRoom(idRoom)
        }
    })

    const handleChange = (event) => {
        setIdRoom(event.target.value);
    };

    return (
        <div style={{marginLeft: '20px', width: '500px'}}>
            <FormControl variant="standard" sx={{ m: 1, minWidth: 120, width: '400px' }}>
                <InputLabel id="demo-simple-select-standard-label" sx={{fontSize: '14px', marginBottom: '10px'}}>Vui lòng chọn nhà</InputLabel>
                <Select
                    labelId="demo-simple-select-standard-label"
                    id="demo-simple-select-standard"
                    value={idRoom}
                    onChange={handleChange}
                    label="Room" 
                    style={{marginBottom: '10px'}}
                >
                    {props.dataListhome?.map((room) => (
                        <MenuItem value={room.id} key={room.id} sx={{fontSize: '14px' }}>{room.accomName}</MenuItem>
                    ))}
                </Select>
            </FormControl>
        </div>
    );
}

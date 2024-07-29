import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { useState } from 'react';
import './ListFacilityByCategory.scss';

const ListFacilityByCategory = ({ facilityList, facilityCateName, data, setData }) => {
    const [expanded, setExpanded] = useState(false);
    const handleChange = (panel) => (event, isExpanded) => {
        setExpanded(isExpanded ? panel : false);
    };

    const handleClose = () => {
        setExpanded(false);
    };

    const handleChangeBox = (event) => {
        if (event.target.checked) {
            setData([...data, event.target.value]);
        } else {
            setData(data.filter((item) => item !== event.target.value));
        }
    };
    return (
        <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
            <AccordionSummary expandIcon={<ExpandMoreIcon />} aria-controls="panel1bh-content" id="panel1bh-header">
                <div className="title">{facilityCateName}</div>
            </AccordionSummary>
            <AccordionDetails aria-controls="panel1bh-content" id="panel1bh-header" style={{ display: 'flex' }}>
                <div className="row">
                    {facilityList?.map((facility, index) => (
                        <div className="col l-6" key={index}>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        onChange={handleChangeBox}
                                        checked={data?.includes(facility.facilityCode)}
                                        value={facility.facilityCode}
                                        sx={{ '& .MuiSvgIcon-root': { fontSize: 28 } }}
                                    />
                                }
                                label={facility.facilityName}
                                sx={{ '.MuiTypography-root': { fontSize: 17 } }}
                            />
                        </div>
                    ))}
                </div>
            </AccordionDetails>
        </Accordion>
    );
};
export default ListFacilityByCategory;

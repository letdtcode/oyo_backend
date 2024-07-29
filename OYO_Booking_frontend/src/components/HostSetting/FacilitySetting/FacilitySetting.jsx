import { useState, useEffect } from 'react';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ConvenientItem from '~/components/ConvenientItem/ConvenientItem';
import publicFacilityAPI from '~/services/apis/publicAPI/publicFacilityAPI';
import { useSnackbar } from 'notistack';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import { useParams } from 'react-router-dom';
import './FacilitySetting.scss';

export default function FacilitySetting() {
    const [expanded, setExpanded] = useState(false);
    const params = useParams();
    const [loading, setLoading] = useState(true);
    const [dataListCatagoryConvenient, setDataListCategoryConvenient] = useState([]);
    const [facilitiesApply, setFacilitiesApply] = useState([]);
    const facilitiesList = dataListCatagoryConvenient.flatMap((category) => category.infoFacilityList);

    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        publicFacilityAPI.getAllDataFacility().then((dataResponse) => {
            setDataListCategoryConvenient(dataResponse.data);
        });
        partnerManageAccomAPI.getFacilitiesAccom(params.idHome).then((dataResponse) => {
            const temp = dataResponse.data.facilities.flatMap((result) => {
                return result.facilityCode;
            });
            setFacilitiesApply(temp);
            setLoading(false);
        });
    }, [params?.idHome]);

    const handleChange = (panel) => (event, isExpanded) => {
        setExpanded(isExpanded ? panel : false);
    };

    const handleClose = () => {
        setExpanded(false);
    };

    const nameFacilities = `${facilitiesApply
        .map((code) => facilitiesList.find((item) => item.facilityCode === code)?.facilityName)
        .filter(Boolean)
        .join(', ')}`;

    const handleSave = (e) => {
        e.preventDefault();
        const newData = {
            id: params?.idHome,
            data: facilitiesApply
        };
        partnerManageAccomAPI
            .updateFacilitiesAccom(newData)
            .then((dataResponse) => {
                enqueueSnackbar('Cập nhật thành công', { variant: 'success' });
            })
            .catch((error) => {
                enqueueSnackbar(error.response?.data.message, { variant: 'error' });
            });
    };

    return (
        <div className="facility-setting">
            <h3 className="facility-setting__title">Tiện ích</h3>
            <form onSubmit={handleSave}>
                <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel1bh-content"
                        id="panel1bh-header"
                        className="accordion-summary"
                    >
                        <p className="accordion-summary__title">Tiện ích</p>
                        <p className="accordion-summary__subtitle">{nameFacilities}</p>
                    </AccordionSummary>

                    {loading ? (
                        <></>
                    ) : (
                        <AccordionDetails className="accordion-details">
                            {dataListCatagoryConvenient?.map((child, index) => (
                                <div key={index} className="accordion-details__category">
                                    <ConvenientItem
                                        data={facilitiesApply}
                                        setData={setFacilitiesApply}
                                        dataConveni={child.infoFacilityList}
                                        name={child.faciCateName}
                                    />
                                </div>
                            ))}
                            <div className="accordion-details__btn">
                                <p onClick={handleClose} className="accordion-details__btn-close">
                                    Hủy
                                </p>
                                <button type="submit" className="accordion-details__btn-save">
                                    Lưu
                                </button>
                            </div>
                        </AccordionDetails>
                    )}
                </Accordion>
            </form>
        </div>
    );
}

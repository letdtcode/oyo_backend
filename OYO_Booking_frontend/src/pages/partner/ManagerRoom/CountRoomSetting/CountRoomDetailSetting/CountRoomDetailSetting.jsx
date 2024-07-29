import { useParams } from 'react-router-dom';
import { typeRoom } from '~/models/roomHome';
import CountNumber from '~/components/CountNumber/CountNumber';
import CustomInput from '~/assets/custom/CustomInput';
import MenuItem from '@mui/material/MenuItem';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import CircularProgress from '@mui/material/CircularProgress';
import './CountRoomDetailSetting.scss';
import { useEffect, useState } from 'react';
import publicTypeBedAPI from '~/services/apis/publicAPI/publicTypeBedAPI';
import partnerManageAccomAPI from '~/services/apis/partnerAPI/partnerManageAccomAPI';
import { useSnackbar } from 'notistack';

const CountRoomDetailSetting = ({ allAccomCategory }) => {
    const [numRoom, setNumRoom] = useState(typeRoom);
    const [roomSetting, setRoomSetting] = useState(null);
    const [bedRooms, setBedRooms] = useState([]);
    const [allTypeBed, setAllTypeBed] = useState(null);
    const [expanded, setExpanded] = useState(false);
    const [loading, setLoading] = useState(true);
    const params = useParams();
    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        publicTypeBedAPI.getAllTypeBed().then((res) => {
            setAllTypeBed(res.data.content);
        });
    }, []);

    useEffect(() => {
        if (numRoom[0].number > bedRooms.length) {
            setBedRooms((prevBedRooms) => [...prevBedRooms, 'TYPE_BED_003']);
        } else if (numRoom[0].number === bedRooms.length - 1) {
            setBedRooms((prevBedRooms) => prevBedRooms.slice(0, -1));
        }
    }, [numRoom[0].number]);

    useEffect(() => {
        partnerManageAccomAPI.getRoomSetting(`${params.idHome}`).then((dataRoom) => {
            setNumRoom(
                numRoom.map((room) => ({
                    ...room,
                    number: dataRoom?.data[room.key] || 0
                }))
            );
            setBedRooms(dataRoom?.data?.bedRooms.typeBeds.flatMap((bed) => bed.typeBedCode));
            setRoomSetting(dataRoom?.data);
            setLoading(false);
        });
    }, [params.idHome]);

    const handleChange = (panel) => (event, isExpanded) => {
        setExpanded(isExpanded ? panel : false);
    };

    const handleClose = () => {
        setExpanded(false);
    };

    const handleSaveRoom = (e) => {
        e.preventDefault();
        const newData = {
            id: params?.idHome,
            data: {
                accomCateName: roomSetting?.accomCateName,
                numPeople: roomSetting?.numPeople,
                numKitchen: numRoom[2].number,
                numBathRoom: numRoom[1].number,
                typeBedCodes: bedRooms
            }
        };

        partnerManageAccomAPI
            .updateRoomSetting(newData)
            .then((res) => {
                enqueueSnackbar('Cập nhật thành công', {
                    variant: 'success'
                });
            })
            .catch((err) => {
                enqueueSnackbar(err, {
                    variant: 'error'
                });
            });
    };

    const onChange = (value, index) => {
        const updatedBedRooms = [...bedRooms];
        updatedBedRooms[index] = value;
        setBedRooms(updatedBedRooms);
    };

    if (loading) {
        return (
            <div className="count-room-detail-setting__loading">
                <CircularProgress />
            </div>
        );
    }

    return (
        <div className="count-room-detail-setting">
            <form onSubmit={handleSaveRoom}>
                <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls="panel1bh-content"
                        id="panel1bh-header"
                        className="accordion-summary"
                    >
                        <p className="accordion-summary__title">Loại chỗ ở</p>
                        <CustomInput
                            select={true}
                            value={roomSetting?.accomCateName || ''}
                            width={500}
                            onChange={(e) => setRoomSetting({ ...roomSetting, accomCateName: e.target.value })}
                            content={allAccomCategory.map((option, index) => (
                                <MenuItem key={index} value={option.accomCateName}>
                                    {option.accomCateName}
                                </MenuItem>
                            ))}
                        />
                    </AccordionSummary>
                    <AccordionDetails className="accordion-details">
                        <div className="accordion-details__section row ">
                            <p className="accordion-details__label col l-4 m-6 c-12">Số lượng khách</p>
                            <CustomInput
                                className="col l-4 m-6 c-12"
                                value={roomSetting?.numPeople}
                                width={500}
                                onChange={(e) => {
                                    setRoomSetting({
                                        ...roomSetting,
                                        numPeople: parseInt(e.target.value) ? parseInt(e.target.value) : ''
                                    });
                                }}
                            />
                        </div>

                        <div className="accordion-details__count-room">
                            {numRoom?.map((room, index) => (
                                <div key={index} className='accordion-details__count-room__item'>
                                    <p className='accordion-details__count-room__item__header'>
                                        {room.name} ({room.number})
                                    </p>
                                    <CountNumber
                                        keyType={room.key}
                                        data={numRoom}
                                        number={room.number}
                                        setData={setNumRoom}
                                    />
                                </div>
                            ))}
                        </div>
                    </AccordionDetails>
                    <AccordionDetails className="accordion-details">
                        <div className="accordion-details__bedroom row">
                            {bedRooms?.map((bed, index) => (
                                <div key={index} className="accordion-details__option-bed col l-6 m-6 c-12">
                                    <p>Phòng ngủ số {index + 1}</p>
                                    <CustomInput
                                        size="small"
                                        value={bed || ''}
                                        onChange={(e) => onChange(e.target.value, index)}
                                        select={true}
                                        content={allTypeBed?.map((option, i) => (
                                            <MenuItem key={i} value={option.typeBedCode}>
                                                {option.typeBedName}
                                            </MenuItem>
                                        ))}
                                    />
                                </div>
                            ))}
                        </div>
                    </AccordionDetails>
                    <AccordionDetails className="accordion-details">
                        <div className="accordion-details__btn">
                            <p onClick={handleClose} className="accordion-details__btn-close">
                                Hủy
                            </p>
                            <button type="submit" className="accordion-details__btn-save">
                                Lưu
                            </button>
                        </div>
                    </AccordionDetails>
                </Accordion>
            </form>
        </div>
    );
};

export default CountRoomDetailSetting;

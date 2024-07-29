import './CalendarDateBooked.scss';
import React, { useState, useEffect } from 'react';
import Timeline from 'react-calendar-timeline';
import 'react-calendar-timeline/lib/Timeline.css';
import moment from 'moment';

export default function CalendarDateBooked({ accomPriceCustom }) {
    const [loading, setLoading] = useState(true);
    const [groups, setGroups] = useState([]);
    const [items, setItems] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const groupData = accomPriceCustom.map((item) => ({
                id: item.accomId,
                title: item.accomName
            }));
            setGroups(groupData);

            const itemData = [];
            const currentDate = moment().subtract(1, 'day');

            accomPriceCustom.forEach((accom) => {
                if (accom.rangeDateBookingList.length > 0) {
                    accom.rangeDateBookingList.forEach((dateBooking, index) => {
                        const startDate = moment(dateBooking.dateStart, 'DD/MM/YYYY');
                        const endDate = moment(dateBooking.dateEnd, 'DD/MM/YYYY');

                        if (startDate.isBefore(currentDate) || endDate.isBefore(currentDate)) {
                            return;
                        }

                        itemData.push({
                            id: `${accom.accomId}-${index}`,
                            group: accom.accomId,
                            title: dateBooking.nameCustomer,
                            start_time: startDate.add(12, 'hour'),
                            end_time: endDate.add(12, 'hour')
                        });
                    });
                }
            });
            setItems(itemData);
            setLoading(false);
        };

        if (accomPriceCustom.length > 0) {
            fetchData();
        }
    }, [accomPriceCustom]);


    const sortGroupsByItemCount = () => {
        const sortedGroups = [...groups];
        sortedGroups.sort((groupA, groupB) => {
            const countA = items.filter(item => item.group === groupA.id).length;
            const countB = items.filter(item => item.group === groupB.id).length;
            return countB - countA; 
        });
        
      
        return sortedGroups;
    };

    return (
        <div className="calendar-manage">
            {loading ? (
                <div>Loading...</div>
            ) : (
                <div>
                    <Timeline
                        lineHeight={35}
                        fullUpdate
                        showCursorLine
                        sidebarWidth={300}
                        canMove={true}
                        groups={sortGroupsByItemCount()} // Use sorted groups here
                        items={items}
                        defaultTimeStart={moment().add(0, 'hour')}
                        defaultTimeEnd={moment().add(14, 'day')}
                        canResize={false}
                    />
                </div>
            )}
        </div>
    );
}

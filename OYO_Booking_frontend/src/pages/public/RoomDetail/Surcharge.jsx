// import Accordion from '@mui/material/Accordion';
// import AccordionSummary from '@mui/material/AccordionSummary';
// import AccordionDetails from '@mui/material/AccordionDetails';
// import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
// import { useState, useEffect } from 'react';
// import formatPrice from '~/utils/formatPrice';
// import { t } from 'i18next';
// export default function SurchargeList(props) {
//     const [expanded, setExpanded] = useState(false);
//     const handleChange = (panel) => (event, isExpanded) => {
//         setExpanded(isExpanded ? panel : false);
//     };

//     const handleClose = () => {
//         setExpanded(false);
//     };
//     const totalCost = props?.data?.reduce((sum, surcharge) => sum + surcharge.cost, 0);
//     return (
//         <>
//             {totalCost > 0 && (
//                 <Accordion
//                     className="list__surcharge"
//                     expanded={expanded === 'panel1'}
//                     onChange={handleChange('panel1')}
//                 >
//                     <AccordionSummary
//                         className="price-total"
//                         expandIcon={<ExpandMoreIcon />}
//                         aria-controls="panel1bh-content"
//                         id="panel1bh-header"
//                     >
//                         <div className="title-price">
//                             <p className="name-surcharge">{t('common.surcharge')}</p>
//                         </div>
//                         <div className="real-price">
//                             <p className="cost-surcharge">{formatPrice(totalCost)}</p>
//                         </div>
//                     </AccordionSummary>
//                     <AccordionDetails>
//                         {props.data?.map((sur, index) => (
//                             <div className="price-total" key={index}>
//                                 <div className="title-price">
//                                     <p className="name-surcharge">{`${sur?.surchargeName}`}</p>
//                                 </div>
//                                 <div className="real-price">
//                                     <p className="cost-surcharge">{formatPrice(sur?.cost)}</p>
//                                 </div>
//                             </div>
//                         ))}
//                     </AccordionDetails>
//                 </Accordion>
//             )}
//         </>
//     );
// }

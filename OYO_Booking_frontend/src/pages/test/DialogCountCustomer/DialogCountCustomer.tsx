// import * as React from 'react';
// import Button from '@mui/material/Button';
// import Dialog from '@mui/material/Dialog';
// import DialogActions from '@mui/material/DialogActions';
// import DialogContent from '@mui/material/DialogContent';
// import DialogTitle from '@mui/material/DialogTitle';

// // import { useSnackbar } from 'notistack';
// // import { AxiosError } from 'axios';

// import './DialogCountCustomer.scss';

// export default function DialogCountCustomer(props: any) {
//     const [open, setOpen] = React.useState(false);

//     // const { enqueueSnackbar } = useSnackbar();

//     const handleClickOpen = () => {
//         setOpen(true);
//     };

//     const handleClose = () => {
//         setOpen(false);
//     };

//     return (
//         <div className="dialog-count-of__customer">
//             <p onClick={handleClickOpen} className="edit-count-bed" style={{ color: 'black', fontSize: '16px' }}>
//                 Chỉnh sửa
//             </p>
//             <Dialog
//                 open={open}
//                 onClose={handleClose}
//                 aria-labelledby="alert-dialog-title"
//                 aria-describedby="alert-dialog-description"
//             >
//                 <div>
//                     <DialogTitle
//                         id="alert-dialog-title"
//                         sx={{ fontSize: '18px', fontWeight: 'bold', marginBottom: '20px', width: '300px' }}
//                     >
//                         {'Khách'}
//                     </DialogTitle>
//                     <DialogContent sx={{ fontWeight: 'bold' }}>
//                         <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '10px' }}>
//                             <p style={{ fontSize: '14px', margin: '8px 0' }}>Người lớn</p>
//                             <input type='number' className='input-count__customer' style={{ width: '80px', padding: '0 10px' }}/>
//                         </div>
//                         <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '10px' }}>
//                             <p style={{ fontSize: '14px', margin: '8px 0' }}>Trẻ em</p>
//                             <input type='number' className='input-count__customer' style={{ width: '80px', padding: '0 10px' }}/>
//                         </div>
//                     </DialogContent>
//                 </div>

//                 <DialogActions>
//                     <Button onClick={handleClose} color="error" sx={{ fontSize: '14px', textTransform: 'none' }}>
//                         Close
//                     </Button>
//                     <Button autoFocus sx={{ fontSize: '14px', textTransform: 'none' }}>
//                         Lưu
//                     </Button>
//                 </DialogActions>
//             </Dialog>
//         </div>
//     );
// }

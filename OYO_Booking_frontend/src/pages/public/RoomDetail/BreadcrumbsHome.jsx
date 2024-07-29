import Breadcrumbs from '@mui/material/Breadcrumbs';
import Link from '@mui/material/Link';
import { useNavigate } from 'react-router-dom';
import NavigateNextIcon from '@mui/icons-material/NavigateNext';
import { decodeAddress } from '~/utils/decodeAddress';
import { useDispatch } from 'react-redux';
import filterAcomSlice from '~/redux/filterAccom';

export default function BreadcrumbsHome(props) {
    const { data } = props;
    const address = decodeAddress(data);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const handleLink = ({ filter, code }) => {
        const filterAcom = {
            [filter]: code
        }
        dispatch(filterAcomSlice.actions.reset());
        dispatch(filterAcomSlice.actions.address(filterAcom));
        navigate(`/list-accom`);
    };
    return (
        <Breadcrumbs aria-label="breadcrumb" className='breadcrumb' separator={<NavigateNextIcon fontSize="small" />}>
            <Link
                underline="hover"
                color="inherit"
                onClick={() => handleLink({ filter: 'provinceCode', code: address.provinceCode })}
            >
                {address?.provinceName}
            </Link>
            <Link
                underline="hover"
                color="inherit"
                onClick={() => handleLink({ filter: 'districtCode', code: address.districtCode })}
            >
                {address?.districtName}
            </Link>
            <Link
                underline="hover"
                color="inherit"
                onClick={() => handleLink({ filter: 'wardCode', code: address.wardCode })}
            >
                {address?.wardName}
            </Link>
        </Breadcrumbs>
    );
}

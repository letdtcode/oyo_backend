import { id } from "date-fns/locale";

const SidebarData = {
    Overview: [
        {
            id: 0,
            display_name: 'Trang chủ',
            route: '/admin',
            icon: 'bx bx-category-alt'
        }
    ],
    Manage: [
        {
            id: 2,
            display_name: 'Người dùng',
            route: '/admin/users',
            icon: 'bx bx-user-pin'
        },
        {
            id: 3,
            display_name: 'Nơi cho thuê',
            route: '/admin/accomplaces',
            icon: 'bx bxs-building-house'
        },
        {
            id: 4,
            display_name: 'Loại hình cho thuê',
            route: '/admin/accomcategories',
            icon: 'bx bx-user-pin'
        },
        {
            id: 5,
            display_name: 'Loại giường',
            route: '/admin/typebeds',
            icon: 'bx bx-bed'
        },
        {
            id: 6,
            display_name: 'Tiện ích',
            route: '/admin/facilities',
            icon: 'bx bx-package'
        },
        {
            id: 7,
            display_name: 'Loại tiện ích',
            route: '/admin/facilitycategories',
            icon: 'bx bxl-dropbox'
        },
        // {
        //     id: 8,
        //     display_name: 'Giảm giá nhà',
        //     route: '/admin/discount',
        //     icon: 'bx bx-gift'
        // },
        {
            id: 8,
            display_name: 'Danh mục phụ phí',
            route: '/admin/surchargecategories',
            icon: 'bx bx-card'
        },
        {
            id: 9,
            route: '/admin/new-accom',
            display_name: 'Duyệt chỗ nghỉ mới',
            icon: 'bx bx-check'
        }
    ],
    Setting: [
        {
            id: 10,
            display_name: 'Cài đặt',
            route: '/admin/settings',
            icon: 'bx bx-cog'
        }
    ],
    All: [
        {
            id: 0,
            display_name: 'Trang chủ',
            route: '/admin',
            icon: 'bx bx-category-alt'
        },
        {
            id: 1,
            display_name: 'Đặt phòng',
            route: '/admin/booking',
            icon: 'bx bx-cart'
        },
        {
            id: 2,
            display_name: 'Người dùng',
            route: '/admin/customers',
            icon: 'bx bx-user-pin'
        },
        {
            id: 3,
            display_name: 'Nhà',
            route: '/admin/house',
            icon: 'bx bxs-building-house'
        },
        {
            id: 4,
            display_name: 'Loại phòng',
            route: '/admin/roomcategories',
            icon: 'bx bx-user-pin'
        },
        {
            id: 5,
            display_name: 'Loại giường',
            route: '/admin/bedcategories',
            icon: 'bx bx-bed'
        },
        {
            id: 6,
            display_name: 'Tiện nghi',
            route: '/admin/amenity',
            icon: 'bx bx-package'
        },
        {
            id: 7,
            display_name: 'Loại tiện nghi',
            route: '/admin/amenitycategories',
            icon: 'bx bxl-dropbox'
        },
        {
            id: 8,
            display_name: 'Giảm giá nhà',
            route: '/admin/discount',
            icon: 'bx bx-gift'
        },
        {
            id: 9,
            display_name: 'Danh mục phụ phí',
            route: '/admin/surcharge',
            icon: 'bx bx-card'
        },
        {
            id: 10,
            display_name: 'Cài đặt',
            route: '/admin/settings',
            icon: 'bx bx-cog'
        }
    ]
};

export default SidebarData;

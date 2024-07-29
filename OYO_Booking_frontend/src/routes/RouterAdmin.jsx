import { Routes, Route } from 'react-router-dom';
import DashboardAdmin from '~/pages/admin/DashboardAdmin/DashboardAdmin';
import LayoutUserAdmin from '~/pages/admin/LayoutUserAdmin/LayoutUserAdmin';
import LayoutAccomAdmin from '~/pages/admin/LayoutAccomAdmin/LayoutAccomAdmin';
import LayoutAccomCategoryAdmin from '~/pages/admin/LayoutAccomCategoryAdmin/LayoutAccomCategoryAdmin';
import LayoutTypeBedAdmin from '~/pages/admin/LayoutTypeBedAdmin/LayoutTypeBedAdmin';
import LayoutFacilityAdmin from '~/pages/admin/LayoutFacilityAdmin/LayoutFacilityAdmin';
import LayoutFacilityCategoryAdmin from '~/pages/admin/LayoutFacilityCategoryAdmin/LayoutFacilityCategoryAdmin';
import LayoutSurchargeCategoryAdmin from '~/pages/admin/LayoutSurchargeCategoryAdmin/LayoutSurchargeCategoryAdmin';
import LayoutNewAccom from '~/pages/admin/LayoutNewAccom/LayoutNewAccom';
import NewAccomWaiting from '~/components/Admin/NewAccomWaiting/NewAccomWaiting';
import { Suspense } from 'react';

const RouterAdmin = () => {
    return (
        <Routes>
            <Route
                path="/"
                element={
                    <Suspense>
                        <DashboardAdmin />
                    </Suspense>
                }
            />
            <Route
                path="/users"
                element={
                    <Suspense>
                        <LayoutUserAdmin />
                    </Suspense>
                }
            />
            <Route
                path="/accomplaces"
                element={
                    <Suspense>
                        <LayoutAccomAdmin />
                    </Suspense>
                }
            />
            <Route
                path="/accomcategories"
                element={
                    <Suspense>
                        <LayoutAccomCategoryAdmin />
                    </Suspense>
                }
            />
            <Route
                path="/typebeds"
                element={
                    <Suspense>
                        <LayoutTypeBedAdmin />
                    </Suspense>
                }
            />
            <Route
                path="/facilities"
                element={
                    <Suspense>
                        <LayoutFacilityAdmin />
                    </Suspense>
                }
            />
            <Route
                path="/facilitycategories"
                element={
                    <Suspense>
                        <LayoutFacilityCategoryAdmin />
                    </Suspense>
                }
            />
            <Route
                path="/surchargecategories"
                element={
                    <Suspense>
                        <LayoutSurchargeCategoryAdmin />
                    </Suspense>
                }
            />
            <Route
                path="/new-accom"
                element={
                    <Suspense>
                        <LayoutNewAccom />
                    </Suspense>
                }
            />
            <Route
                path="/new-accom/:id"
                element={
                    <Suspense>
                        <NewAccomWaiting />
                    </Suspense>
                }
            />
        </Routes>
    );
};
export default RouterAdmin;

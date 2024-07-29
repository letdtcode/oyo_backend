import React, { Suspense } from 'react';
import { Routes, Route } from 'react-router-dom';
import { useSelector } from 'react-redux';
import PrivateRoute from '~/components/PrivateRoute/PrivateRoute';
import LayoutAdmin from '~/pages/admin/LayoutAdmin/LayoutAdmin';
import StatisticPage from '~/pages/partner/StatisticPage/StatisticPage';
import ClientPaymentSuccess from '~/pages/client/ClientPaymentSuccess/ClientPaymentSuccess';

const Auth = () => {
    const roles = useSelector((state) => state.user.roles);
    const isAdmin = useSelector((state) => state.user.isAdmin);
    // Admin Page
    const LoginAdmin = React.lazy(() => import('~/pages/admin/LoginAdmin/LoginAdmin'));
    // Public Page
    const ActiveAccountPage = React.lazy(() => import('~/pages/public/ActiveAccountPage/ActiveAccountPage'));
    const HomePage = React.lazy(() => import('../pages/public/HomePage/HomePage'));
    const InfoUserPage = React.lazy(() => import('../pages/public/InfoUserPage/InfoUserPage'));
    const RoomDetail = React.lazy(() => import('../pages/public/RoomDetail/RoomDetail'));
    const NotFoundPage = React.lazy(() => import('../pages/public/NotFoundPage/NotFoundPage'));
    const CongratulationPage = React.lazy(() => import('../pages/test/CongratulationPage/Congratulation'));
    const TestComponet = React.lazy(() => import('~/components/Test/Test'));
    const BookingPage = React.lazy(() => import('~/pages/client/BookingPage/BookingPage'));
    const IntroSettingOwnerPage = React.lazy(() => import('../pages/test/IntroSettingOwnerPage/IntroSettingOwnerPage'));
    const ContactForm = React.lazy(() => import('../pages/public/Contact/Contact'));
    const FavoritesPage = React.lazy(() => import('../pages/client/FavoritesPage/FavoritesPage'));
    const ListAccomPage = React.lazy(() => import('~/pages/public/ListAccomPage/ListAccomPage'));
    const OAuth2RedirectHandler = React.lazy(() => import('~/helper/OAuth2RedirectHandler'));

    // Partner Page
    const BookingTodayPage = React.lazy(() => import('~/pages/partner/BookingTodayPage/BookingTodayPage'));
    const ListRoomOfHost = React.lazy(() => import('../pages/partner/ManagerRoom/ListRoomOfHost'));
    const TransactionHistoryPage = React.lazy(() =>
        import('../pages/partner/TransactionHistoryPage/TransactionHistoryPage')
    );
    const ManagerRoom = React.lazy(() => import('~/pages/partner/ManagerRoom/ManagerRoom'));
    const CreateAccommodationPage = React.lazy(() =>
        import('~/pages/partner/CreateAccommodationPage/CreateAccommodationPage')
    );
    const DashboardPartnerPage = React.lazy(() => import('~/pages/partner/DashboardPartnerPage/DashboardPartnerPage'));
    const CalendarManagePage = React.lazy(() => import('~/pages/partner/CalendarManagePage/CalendarManagePage'));
    const RoomsAndRateManagePage = React.lazy(() =>
        import('~/pages/partner/RoomsAndRateManagePage/RoomsAndRateManagePage')
    );
    // Client Page
    const HistoryBookingPage = React.lazy(() => import('../pages/client/HistoryBookingPage/HistoryBookingPage'));
    return (
        <Routes>
            <Route
                path="/oauth2/redirect"
                element={
                    <Suspense>
                        <OAuth2RedirectHandler />
                    </Suspense>
                }
            ></Route>
            <Route
                path="/active-account"
                element={
                    <Suspense>
                        <ActiveAccountPage />
                    </Suspense>
                }
            />

            <Route
                path="/"
                element={
                    <Suspense>
                        <HomePage />
                    </Suspense>
                }
            />
            <Route
                path="/account/*"
                element={
                    <PrivateRoute
                        element={
                            <Suspense>
                                <InfoUserPage />
                            </Suspense>
                        }
                    />
                }
            />

            <Route
                path="/room-detail/:id"
                element={
                    <Suspense>
                        <RoomDetail />
                    </Suspense>
                }
            />
            <Route
                path="/contact"
                element={
                    <Suspense>
                        <ContactForm />
                    </Suspense>
                }
            />
            <Route
                path="/wishlists"
                element={
                    <Suspense>
                        <FavoritesPage />
                    </Suspense>
                }
            />
            <Route
                path="/congratulation"
                element={
                    <Suspense>
                        <CongratulationPage />
                    </Suspense>
                }
            />
            <Route
                path="/booking"
                element={
                    <PrivateRoute
                        element={
                            <Suspense>
                                <BookingPage />
                            </Suspense>
                        }
                    />
                }
            />
            <Route
                path="/myBooking"
                element={
                    <PrivateRoute
                        element={
                            <Suspense>
                                <HistoryBookingPage />
                            </Suspense>
                        }
                    />
                }
            />

            <Route
                path="/payment/success"
                element={
                    <PrivateRoute
                        element={
                            <Suspense>
                                <ClientPaymentSuccess />
                            </Suspense>
                        }
                    />
                }
            />
            <Route
                path="/test1"
                element={
                    <Suspense>
                        <TestComponet />
                    </Suspense>
                }
            />
            <Route
                path="/intro-host"
                element={
                    <Suspense>
                        <IntroSettingOwnerPage />
                    </Suspense>
                }
            />
            <Route
                path="/host/setting/:idHome"
                element={
                    <PrivateRoute
                        element={
                            <Suspense>
                                <ManagerRoom />
                            </Suspense>
                        }
                    />
                }
            />

            <Route
                path="/host/today"
                element={
                    <PrivateRoute
                        element={
                            <Suspense>
                                <BookingTodayPage />
                            </Suspense>
                        }
                    />
                }
            />

            <Route
                path="/host/transactionhistory"
                element={
                    <PrivateRoute
                        element={
                            <Suspense>
                                <TransactionHistoryPage />
                            </Suspense>
                        }
                    />
                }
            />

            <Route
                path="/host/statistic"
                element={
                    <PrivateRoute
                        element={
                            <Suspense>
                                <StatisticPage />
                            </Suspense>
                        }
                    />
                }
            />
            <Route
                path="/host/createHotel/*"
                element={
                    <PrivateRoute
                        element={
                            <Suspense>
                                <CreateAccommodationPage />
                            </Suspense>
                        }
                    />
                }
            />
            <Route
                path="/host/calendar/*"
                element={
                    <PrivateRoute
                        element={
                            <Suspense>
                                <CalendarManagePage />
                            </Suspense>
                        }
                    />
                }
            />
            <Route
                path="/host/roomsAndRateManager/*"
                element={
                    <PrivateRoute
                        element={
                            <Suspense>
                                <RoomsAndRateManagePage />
                            </Suspense>
                        }
                    />
                }
            />
            <Route
                path="/host/*"
                element={
                    <PrivateRoute
                        element={
                            <Suspense>
                                <DashboardPartnerPage />
                            </Suspense>
                        }
                    />
                }
            />

            <Route
                path="*"
                element={
                    <Suspense>
                        <NotFoundPage />
                    </Suspense>
                }
            />
            <Route
                path="/list-accom"
                element={
                    <Suspense>
                        <ListAccomPage />
                    </Suspense>
                }
            />
            {isAdmin == true && <Route path="/admin/*" element={<LayoutAdmin />} />}

            <Route
                path="/admin/login"
                element={
                    <Suspense>
                        <LoginAdmin />
                    </Suspense>
                }
            />
        </Routes>
    );
};
export default Auth;

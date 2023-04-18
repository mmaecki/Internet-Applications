import React from 'react';
import logo from './logo.svg';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { store } from './store';
import {
  createBrowserRouter,
  RouterProvider,
  Route,
  BrowserRouter,
  Routes,
  useNavigate,
  redirect,
} from "react-router-dom";

import { Provider } from 'react-redux'
import HomePage from './components/HomePage/HomePage'
import { hasSubscribers } from 'diagnostics_channel';
import HubsPackagesList from './components/HubWorkerPackagesList/HubsPackagesList';
import TrucksPackagesList from './components/DriverPackagesList/TruckPackagesList';
import CreatePackage from './components/CreatePackage/CreatePackage';
import CreateMessage from './components/CreateMessage/CreateMessage';
import ClientPackagesList from './components/ClientPackagesList/ClientPackagesList';
import ClientMessagesList from './components/ClientMessagesList/ClientMessagesList';
import {loadClientPackages} from "./store/packages";

const router = createBrowserRouter([
  {
    path: "/",
    element: <div>Hello world!</div>,
  },
]);

function App() {
  const logout = () => {
    localStorage.removeItem('token');
    window.location.replace("http://localhost:3000");
  }

  return (
    <BrowserRouter>
    {
      localStorage.getItem('token') !== null &&
       <button onClick={logout}>Logout</button>
    }
      <Routes>
        <Route path="/" element={<HomePage/>}/>
        <Route path="/clientPackages" element={ <ClientPackagesList/>}/> 
        <Route path="/createClientPackage" element={<CreatePackage/>}/>
        <Route path="/createMessage/:packageId" element={<CreateMessage/>}/>
        <Route path="/hubsPackagesList" element={<HubsPackagesList/>}/>
        <Route path="/driverPackages" element={ <TrucksPackagesList/>}/>
        <Route path="/clientMessages" element={ <ClientMessagesList/>}/>
      </Routes>
    </BrowserRouter>
  );
}

const RdxApp = () => 
  <Provider store={store}>
    <App/>
  </Provider>

export default RdxApp;

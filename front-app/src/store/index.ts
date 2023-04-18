import { configureStore } from '@reduxjs/toolkit';
import logger from 'redux-logger';
import packagesReducer, { loadClientPackages } from './packages';
import loginUserReducer from './loginUser'
import messagesReducer, { loadClientMessages } from './messages';
import hubWorkersPackagesReducer, {loadHubWorkersPackages} from './hubworkersPackages'
import driversPackagesReducer, {loadDriversPackages} from './driversPackages'
import {Simulate} from "react-dom/test-utils";
import load = Simulate.load;
import driversPackages from "./driversPackages";

export const store = configureStore({
    reducer: {
        clientPackages: packagesReducer,
        clientMessages: messagesReducer,
        loginUser: loginUserReducer,
        hubWorkersPackages: hubWorkersPackagesReducer,
        driversPackages: driversPackagesReducer
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat([logger]),
});

export type State = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

// store.dispatch(setUser("John Doe"))
// store.dispatch(loadClientPackages())
// store.dispatch(loadHubWorkersPackages())
// store.dispatch(loadDriversPackages())
// store.dispatch(loadClientMessages())


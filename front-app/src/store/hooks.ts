import { TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux'
import type { State, AppDispatch } from '.'
import type { State as PackagesState } from './packages'
import type { State as HubsPackagesState } from './hubworkersPackages'
import type { State as LoginUserState } from './loginUser'
import type { State as MessagesState } from './messages'
import type { State as TrucksPackagesState } from './driversPackages'


// Typed versions of useDispatch and useSelector hooks
export const useAppDispatch: () => AppDispatch = useDispatch
export const useAppSelector: TypedUseSelectorHook<State> = useSelector


export const useClientPackagesSelector: TypedUseSelectorHook<PackagesState> =
    <T>(f:(state:PackagesState) => T) => useAppSelector((state:State) => f(state.clientPackages))

export const useTruckPackagesSelector: TypedUseSelectorHook<PackagesState> =
    <T>(f:(state:PackagesState) => T) => useAppSelector((state:State) => f(state.clientPackages))

export const useLoginUserSelector: TypedUseSelectorHook<LoginUserState> = 
  <T>(f:(state:LoginUserState) => T) => useAppSelector((state:State) => f(state.loginUser))

export const useHubsPackages: TypedUseSelectorHook<HubsPackagesState> =
  <T>(f:(state:HubsPackagesState) => T) => useAppSelector((state:State) => f(state.hubWorkersPackages))

  export const useClientMessagesSelector:TypedUseSelectorHook<MessagesState>=
    <T>(f:(state:MessagesState) => T) => useAppSelector((state:State) => f(state.clientMessages))

export const useTruckPackages: TypedUseSelectorHook<TrucksPackagesState> =
    <T>(f:(state:TrucksPackagesState) => T) => useAppSelector((state:State) => f(state.driversPackages))
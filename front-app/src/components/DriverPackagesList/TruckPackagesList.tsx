import { useEffect, useState } from "react";
import {useAppDispatch, useTruckPackages, useTruckPackagesSelector} from "../../store/hooks";
import PackageDetailsView from "../PackageDetailsView/PackageDetailsView";
import "./TruckPackagesList.css";
import useInput from "../UseInput/UseInput";
import {loadHubWorkersPackages, setFilter} from "../../store/hubworkersPackages";
import PackagesListView from "../PackagesListView/PackagesListView";
import { loadDriversPackages } from "../../store/driversPackages";

const TrucksPackagesList = () => {

    const packages = useTruckPackages(state => state.driversPackages)
    const [selected, setSelected] = useState<number>(0)
    const dispatch = useAppDispatch()

    const [inputStatus, searchStatus, setSearchStatus] = useInput("", "Filter by status")
    const [inputDestination, searchDestination, setSearchDestination] = useInput("", "Filter by destination")

    useEffect(() => { 
        dispatch(setFilter(searchStatus));
        dispatch(loadDriversPackages()) 
    }, [searchStatus])
    const filteredPackages = packages.filter(p => p.status.includes(searchStatus) && p.destination.includes(searchDestination))

    return (
        <div>
            <h1 className="header">PACKAGES IN TRUCK</h1>
            {inputStatus}
            {inputDestination}
            <div className="content">
                <PackagesListView packages={filteredPackages} selected={selected} setSelected={setSelected}/>
                {filteredPackages && <PackageDetailsView pack={packages[selected]}/>}
            </div>
        </div>
    );
};


export default TrucksPackagesList
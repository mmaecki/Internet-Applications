import { useEffect, useState } from "react"
import { useAppDispatch, useHubsPackages } from "../../store/hooks"
import { loadHubWorkersPackages, setFilter } from "../../store/hubworkersPackages"
// import setFilter from "../../store/hubworkersPackages"
import PackageDetailsView from "../PackageDetailsView/PackageDetailsView"
import PackagesListView from "../PackagesListView/PackagesListView"
import useInput from "../UseInput/UseInput"

const HubsPackagesList = () => {

  const packages = useHubsPackages(state => state.hubworkersPackages)
  const [selected, setSelected] = useState<number>(0)
  const dispatch = useAppDispatch()

  const [inputTitle, searchStatus, setSearchTitle] = useInput("", "Filter by status")
  useEffect(() => { dispatch(setFilter(searchStatus))
    dispatch(loadHubWorkersPackages())
  }, [searchStatus])
  const filteredPackages = packages.filter(p => p.status.includes(searchStatus))
  

return (
  <div>
    <h1 className="header">PACKAGES IN HUB</h1>
    {inputTitle}
    <div className="content">
      <PackagesListView packages={filteredPackages} selected={selected} setSelected={setSelected}/>
      {filteredPackages && <PackageDetailsView pack={packages[selected]}/>}
    </div>
  </div>
);
}

export default HubsPackagesList
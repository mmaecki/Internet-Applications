import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAppDispatch, useClientPackagesSelector } from "../../store/hooks";
import { loadClientMessages } from "../../store/messages";
import { loadClientPackages } from "../../store/packages";
import PackageDetailsView from "../PackageDetailsView/PackageDetailsView";
import PackagesListView from "../PackagesListView/PackagesListView";
import "./ClientPackagesList.css";

const ClientPackagesList = () => {
  const packages = useClientPackagesSelector((state) => state.clientPackages);
  const navigate = useNavigate()
  const [selected, setSelected] = useState<number>(0)

  const dispatch = useAppDispatch()

  useEffect(() => { 
    dispatch(loadClientPackages())
    dispatch(loadClientMessages())
  }, [])

  return (
    <div>
      <h1 className="header">YOUR PACKAGES</h1>
      <div className="content">
        <div style={{width: '100%'}}>
          <PackagesListView packages={packages} selected={selected} setSelected={setSelected}/>
          <button onClick={() => navigate('/createClientPackage')}>
            CREATE PACKAGE
          </button>
        </div>
        {packages && <PackageDetailsView pack={packages[selected]}/>}
      </div> 
    </div>

  );
};

export default ClientPackagesList;

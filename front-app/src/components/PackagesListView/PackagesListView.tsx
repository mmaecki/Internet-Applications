import { Package } from "../../types/Package";
import PackageCardView from "../PackageCardView/PackageCardView";
import ListGroup from "react-bootstrap/ListGroup";
import "./PackagesListView.css"
import { SetStateAction } from "react";

const PackagesListView = ({ packages, selected, setSelected }: { 
    packages: Package[],
    selected: number | undefined,
    setSelected: React.Dispatch<SetStateAction<number>>
  }) => {
  return (
    <div className="wrapper">
      <ListGroup>
      <ListGroup.Item>
        <div className="list-row">
          <div className="header-column">
            <p>Identifier</p>
          </div>
          <div className="header-column">
            <p>Status</p>
          </div>
          <div className="header-column">
            <p>History dates</p>
          </div>
        </div>
      </ListGroup.Item>
        {packages.map((pack, i) => (
          <PackageCardView key={i} pack={pack} selected={i == selected} onClick={() => setSelected(i)}/>
        ))}
      </ListGroup>
    </div>
  );
};

export default PackagesListView;

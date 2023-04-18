import ListGroup from "react-bootstrap/esm/ListGroup";
import { Package } from "../../types/Package";
import './PackageCardView.css'


const PackageCardViewButton = ({ pack, selected, onClick }: { pack: Package, selected?: boolean, onClick: ()=> void }) =>
    <ListGroup.Item onClick={onClick}>
        <div className="list-row">
            <div className="column">
                <p>{pack.packageId}</p>
            </div>
            <div className="column">
                <p>{pack.status}</p>
            </div>
            <div className="history-column column">
                {
                    pack.history.map(his => (
                        his.date
                    ))
                }
            </div>
            <button>
                Change state
            </button>
        </div>
    </ListGroup.Item>

export default PackageCardViewButton
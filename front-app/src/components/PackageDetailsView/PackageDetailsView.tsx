import { Package } from "../../types/Package";
import PackageCardView from "../PackageCardView/PackageCardView";
import ListGroup from "react-bootstrap/ListGroup";
import "./PackageDetailsView.css";
import { Routes, Route, useNavigate, redirect } from "react-router-dom";
import ClientMessagesList from "../ClientMessagesList/ClientMessagesList";
import { useClientMessagesSelector } from "../../store/hooks";

const PackagesDetailsView = ({ pack }: { pack: Package }) => {
  const navigate = useNavigate();
  const messages = useClientMessagesSelector(state => state.clientMessages)

  const Redir = (packageId: number) => {
    return navigate(`/createMessage/${packageId}`);
  };
  return (
    <div className="wrapper-details">
      {pack && (
        <ListGroup>
          <ListGroup.Item className="details-section">
            <h2>Package Details</h2>

            <div className="details-flexbox">
              <div>
                <div>
                  <p className="detail-name">ID</p>
                  <p>{pack.packageId}</p>
                </div>
                <div>
                  <p className="detail-name">Description</p>
                  <p>{pack.description}</p>
                </div>
                <div>
                  <p className="detail-name">Dimensions</p>
                  <p>Length: {pack.length}</p>
                  <p>Height: {pack.height}</p>
                  <p>Width: {pack.width}</p>
                  <p>Weight: {pack.weight}</p>
                </div>
                <div>
                  <p className="detail-name">Origin</p>
                  <p>{pack.origin}</p>
                </div>
                <div>
                  <p className="detail-name">Destination</p>
                  <p>{pack.destination}</p>
                </div>
              </div>

              <div>
                <p className="detail-name">HISTORY</p>
                {pack.history.map((his) => (
                  <div>
                    <div className="column">
                      <p>{his.status}</p>
                    </div>
                    <div className="column">
                      <p>{his.date}</p>
                    </div>
                  </div>
                ))}
                <p className="detail-name">MESSAGES</p>
                {messages && messages.map((mes) => (
                  <div>
                    <div className="column">
                      <p>{mes.subject}</p>
                    </div>
                    <div className="column">
                      <p>{mes.body}</p>
                    </div>
                  </div>
                ))}
                <div>
                  <button onClick={() => Redir(pack.packageId)}>
                    Create a message
                  </button>
                </div>
              </div>
            </div>
          </ListGroup.Item>
        </ListGroup>
      )}
    </div>
  );
};

export default PackagesDetailsView;

import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { useAppDispatch, useClientPackagesSelector } from "../../store/hooks"
import { addClientPackage, createClientPackage } from "../../store/packages"
import { PackageCreateInstruction } from "../PackageCreateInstruction/PackageCreateInstruction"
import PackagesListView from "../PackagesListView/PackagesListView"
import useInput from "../UseInput/UseInput"

const CreatePackage = () => {

  const dispatch = useAppDispatch()

  const [newPackage, setNewPackage] = useState<typeof CreatePackage | undefined>(undefined)

  const username=localStorage.getItem('username')!
  const [inputDescription, description] = useInput("", "description")
  const [inputHeight, height] = useInput("", "height")
  const [inputWidth, width] = useInput("", "width")
  const [inputLength, length] = useInput("", "length")
  const [inputWeight, weight] = useInput("", "weight")
  const [inputOrigin, origin] = useInput("", "origin")
  const [inputDestination, destination] = useInput("", "destination")

  const addPackage = () => {
    dispatch(createClientPackage({description: description, height: parseInt(height), width: parseInt(width), 
      length: parseInt(length), weight: parseInt(weight), origin: origin, destination: destination,
      client: {username: username}}))
      navigate('/clientPackages')
  }

  const navigate = useNavigate()
  
  return <div>
    <h1>CREATE PACKAGE FOR CLIENT</h1>
    <PackageCreateInstruction/>
    {inputDescription}
    <br/>
    {inputHeight}
    <br/>
    {inputWidth}
    <br/>
    {inputLength}
    <br/>
    {inputWeight}
    <br/>
    {inputOrigin}
    <br/>
    {inputDestination}
    <br/>
    <button onClick={addPackage}>
        Add package
      </button>
  </div>  
}

export default CreatePackage
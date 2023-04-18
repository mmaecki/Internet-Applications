import { SetStateAction, useState } from "react"

// This is a function (custom hook) that isolates the behaviour of an input field (can be made generic)
const useInput = (initialValue:string, placeholder:string)
    :([JSX.Element, string, React.Dispatch<SetStateAction<string>>]) => {

    const [search, setSearch] = useState<string>(initialValue)

    const input = <input type="text"
        placeholder={placeholder}
        value={search}
        onChange={e => setSearch(e.target.value)} />

    return [input, search, setSearch]
}

export default useInput
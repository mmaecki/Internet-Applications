export interface Package {
  packageId: number,
  description:string,
  height: number,
  width: number,
  length: number,
  weight: number,
  origin: string,
  destination:string,
  history: History[],
  client: {username: string},
  status: string
}

export interface CreatePackage {
  description:string,
  height: number,
  width: number,
  length: number,
  weight:number,
  origin:string,
  destination:string,
  client: {username: string},
}

export interface History {
  status: string,
  date: string
}


// export interface Subject {
// [x: string]: any;
//   id: number;
//   name: string;
 
// }

// export interface User {
//   id: number;
//   userName: string;
//   email: string;
//   password: string;
//   subscription: Subject[];
// }
export interface Subject {
  id: number;
  title: string;
  date: Date;
  description: string;
  users?: User[];
}

export interface User {
  subscription: Subject[];
  id: number;
  userName: string;
  email: string;
  password: string;
  subjects?: Subject[];
}

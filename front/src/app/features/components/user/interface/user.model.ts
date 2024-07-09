

export interface Subject {
  id: number;
  name: string;
 
}

export interface User {
  id: number;
  userName: string;
  email: string;
  password: string;
  subscription: Subject[];
}

// src/app/models/user-dto.model.ts

export interface Subject {
  id: number;
  name: string;
  // Ajoutez d'autres champs nécessaires
}

export interface User {
  id: number;
  userName: string;
  email: string;
  password: string;
  subscription: Subject[];
}

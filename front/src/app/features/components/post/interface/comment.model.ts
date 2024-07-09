
export interface Comment {
    id?: number;
    description: string;
    date?: Date;
    userId: number;
    username: string;
  }

  export interface CreateComment {
    postId: number;
    userId: number;
    description: string;
    
  }
  
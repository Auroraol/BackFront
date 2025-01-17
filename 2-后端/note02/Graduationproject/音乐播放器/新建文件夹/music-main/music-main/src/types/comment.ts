import { IPLocation } from '.';
import { IUser } from './user';

export interface IComment {
  user: IUser;
  beReplied: IComment[];
  status: number;
  commentId: number;
  content: string;
  time: number;
  timeStr: string;
  likedCount: number;
  parentCommentId: number;
  repliedMark: null;
  ipLocation: IPLocation;
  liked: boolean;
}

export interface IComments {
  cursor: string;
  hasMore: boolean;
  totalCount: number;
  comments: IComment[];
}

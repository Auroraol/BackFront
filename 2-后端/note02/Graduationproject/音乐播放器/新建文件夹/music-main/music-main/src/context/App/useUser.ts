import { useContext } from 'react';
import { IUser } from '../../types/user';
import { AppContext } from './App';

const useUser = (): [IUser | undefined, (user: IUser) => void] => {
  const { user, setUser } = useContext(AppContext);

  return [user, setUser];
};

export default useUser;

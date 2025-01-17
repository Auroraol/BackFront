import React, { useContext } from 'react';
import { useUser } from '../../context/App';
import usePlaylists from '../../fetchers/usePlaylists';
import Playlists from '../../components/Playlists';
import Auth from '../../components/Auth';
import { AppContext } from '../../context/App/App';

interface Props {}

const Library: React.FC<Props> = () => {
  const [user] = useUser();
  const { isDesktop } = useContext(AppContext);

  const [myList, otherList] = usePlaylists(user?.userId);

  if (!user?.userId && !isDesktop) {
    return <Auth page />;
  }

  return (
    <div className='px-2 py-4 pb-36'>
      <Playlists list={myList} />
      <Playlists list={otherList} />
    </div>
  );
};

export default Library;

import { FC } from 'react';
import { IArtist } from '../../types/playlist';
import Image from '../Image';

interface Props {
  className?: string;
  data: IArtist;
}

const Artist: FC<Props> = ({ className, data }) => {
  const { name, id, picUrl } = data;

  return (
    <div className='p-2 flex items-center rounded-md hover:bg-active'>
      <Image src={picUrl} className='h-12 w-12 rounded-md' />
      <div className='ml-2 flex items-center h-12'>{name}</div>
    </div>
  );
};

export default Artist;

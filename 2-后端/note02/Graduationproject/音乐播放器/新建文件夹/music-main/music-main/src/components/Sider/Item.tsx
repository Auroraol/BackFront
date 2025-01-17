import { FC, MouseEvent, ReactNode } from 'react';
import cls from 'classnames';

interface Props {
  children: ReactNode;
  onClick?: (e: MouseEvent) => void;
  active?: boolean;
}

const Item: FC<Props> = ({ children, active, onClick }) => {
  return (
    <div
      className={cls(
        'px-3 h-9 rounded-md flex items-center cursor-pointer mb-1 hover:bg-active',
        {
          'bg-active': active,
        }
      )}
      onClick={onClick}
    >
      {children}
    </div>
  );
};

export default Item;

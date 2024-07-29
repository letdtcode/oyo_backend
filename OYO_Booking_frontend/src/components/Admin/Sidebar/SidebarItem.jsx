const SidebarItem = ({ active, title, icon }) => {
  const activeStyle = active ? "active" : "";
  return (
    <div className="sidebar__item">
      <div className={`sidebar__item-inner ${activeStyle}`}>
        <i className={icon}></i>
        <span>{title}</span>
      </div>
    </div>
  );
};
export default SidebarItem;

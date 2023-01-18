/*
 기본 홈 Page 구성  
 */

// Material Dashboard 2 React example components
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";

function SearchAll() {
  return (
    <DashboardLayout>
      <DashboardNavbar />
      <div>
        <h1>검색 custom 공간</h1>
      </div>
    </DashboardLayout>
  );
}

export default SearchAll;
/**
=========================================================
* Material Dashboard 2 React - v2.1.0
=========================================================
*/

// react-router components
// import { Link } from "react-router-dom";

// prop-types is a library for typechecking of props
import PropTypes from "prop-types";

// @mui material components
// import Card from "@mui/material/Card";
// import MuiLink from "@mui/material/Link";
// import Button from "@mui/material/Button";
// import Modal from "@mui/material/Modal";
import Chip from "@mui/material/Chip";
import Stack from "@mui/material/Stack";

// Material Dashboard 2 React components
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
// import MDAvatar from "components/MDAvatar";
// import MDButton from "components/MDButton";

// Modal
import axios from "axios";
import { useState, useEffect } from "react";
import { Modal } from "@mui/material";
import { useSelector } from "react-redux";
// import { Box } from "@mui/material";

// Icon
import SettingsIcon from "@mui/icons-material/Settings";
import SkillSetBox from "../SettingModal/SkillSetting";

// import { useState } from "react";

function MySkill(pageUser) {
  const [open5, setOpen5] = useState(false);
  const SkillSetOpen = () => setOpen5(true);
  const SkillSetClose = () => setOpen5(false);
  const [IconHovered3, setIconHovered3] = useState(false);

  // const [SkillJobDataGet, setSkillJobData] = useState([]);
  const [JobDataGet, setJobData] = useState([]);
  const [SkillDataGet, setSkillData] = useState([]);

  // eslint-disable-next-line
  const SkillpageUser = pageUser.pageUser;
  const accessToken = useSelector((state) => state.authToken.accessToken);

  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_PORT_GLOBAL}/api/v1/area/${SkillpageUser}`, {
        headers: { Authorization: `Bearer ${accessToken}` },
      })
      .then((res) => {
        // setSkillJobData(res.data);
        setJobData(res.data.job);
        setSkillData(res.data.skill);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  return (
    <MDBox
      sx={{
        width: "auto",
        height: "auto",
        backgroundColor: "#1C1A25",
        marginTop: "30px",
        marginLeft: "30px",
        border: "1px solid #ffffff",
        borderRadius: "21px",
      }}
      style={{
        display: "flex",
        flexDirection: "column",
      }}
    >
      <Modal open={open5} onClose={SkillSetClose}>
        <SkillSetBox pageUser={pageUser} />
      </Modal>
      <MDBox // ?????? ?????? ??? ?????? ?????? ?????? ?????? ?????? ??????
        sx={{ margin: "15px" }}
      >
        <MDBox // ?????? ?????? ??? ?????? ?????? ????????? ?????? ??????
        >
          <MDTypography
            sx={{
              fontSize: "25px",
              marginLeft: "10px",
              float: "left",
            }}
          >
            ?????? ?????? ??? ?????? ??????
          </MDTypography>
          <MDBox // ?????? ????????? ?????? ??????
            style={{
              float: "right",
            }}
          >
            <SettingsIcon
              sx={{
                width: "25px",
                height: "25px",
              }}
              onClick={SkillSetOpen}
              onMouseEnter={() => setIconHovered3(true)}
              onMouseLeave={() => setIconHovered3(false)}
              color={IconHovered3 ? "white" : "gray"}
            />
          </MDBox>
        </MDBox>
        <MDBox // ?????? ?????? ??????
          sx={{
            marginTop: "50px",
            marginLeft: "20px",
          }}
        >
          <MDTypography
            sx={{
              color: "gray",
            }}
          >
            ?????? ?????? (?????? 3???)
          </MDTypography>
          <MDBox // ?????? ?????? ?????? ?????? ??????
            sx={{
              marginTop: "15px",
            }}
          >
            {/* <Stack direction="row" spacing={1}>
              <Chip label="Python" color="primary" sx={{ fontWeight: "medium" }} />
              <Chip label="React" color="primary" sx={{ fontWeight: "medium" }} />
              <Chip label="Django" color="primary" sx={{ fontWeight: "medium" }} />
            </Stack> */}
            {/* <Stack direction="row" spacing={1}>
              <Chip label="Python" color="primary" sx={{ fontWeight: "medium" }} />
              <Chip label="React" color="primary" sx={{ fontWeight: "medium" }} />
              <Chip label="Django" color="primary" sx={{ fontWeight: "medium" }} />
            </Stack> */}
            {SkillDataGet.length === 0 ? (
              <Stack direction="row" spacing={1}>
                <Chip label="??????" color="primary" sx={{ fontWeight: "medium" }} />
              </Stack>
            ) : (
              <Stack direction="row" spacing={1}>
                {SkillDataGet.map((skill) => (
                  <Chip label={skill} color="primary" sx={{ fontWeight: "medium" }} />
                ))}
              </Stack>
            )}
          </MDBox>
        </MDBox>
        <MDBox // ?????? ?????? ??????
          sx={{
            marginTop: "20px",
            marginLeft: "20px",
          }}
        >
          <MDTypography
            sx={{
              color: "gray",
            }}
          >
            ?????? ?????? (?????? 3???)
          </MDTypography>
          <MDBox // ?????? ?????? ?????? ?????? ??????
            sx={{
              marginTop: "15px",
            }}
          >
            {/* <Stack direction="row" spacing={1}>
              <Chip label="???????????????" color="success" sx={{ fontWeight: "medium" }} />
              <Chip label="?????????" color="success" sx={{ fontWeight: "medium" }} />
              <Chip label="??????????????????" color="success" sx={{ fontWeight: "medium" }} />
            </Stack> */}
            {JobDataGet.length === 0 ? (
              <Stack direction="row" spacing={1}>
                <Chip label="??????" color="success" sx={{ fontWeight: "medium" }} />
              </Stack>
            ) : (
              <Stack direction="row" spacing={1}>
                {JobDataGet.map((job) => (
                  <Chip label={job} color="success" sx={{ fontWeight: "medium" }} />
                ))}
              </Stack>
            )}
          </MDBox>
        </MDBox>
      </MDBox>
    </MDBox>
  );
}

// Default Vlaue
// HomeCard.defaultProps = {
//   commentList: null,
// };

MySkill.propTypes = {
  // eslint-disable-next-line
  pageUser: PropTypes.number.isRequired,
  // profileImage: PropTypes.string.isRequired,
  // nickname: PropTypes.string.isRequired,
  // email: PropTypes.string.isRequired,
  // introduction: PropTypes.string.isRequired,
  // group: PropTypes.string.isRequired,
  // FollowingCount: PropTypes.number.isRequired,
  // FollowerCount: PropTypes.number.isRequired,
};

export default MySkill;

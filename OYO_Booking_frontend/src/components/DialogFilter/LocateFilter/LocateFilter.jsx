import { FormControl, InputLabel } from "@mui/material";
import Select from "@mui/material/Select";
import { LocateContext } from "~/contexts/LocateContext";
import { useContext } from "react";
import MenuItem from "@mui/material/MenuItem";
import FormHelperText from "@mui/material/FormHelperText";
import { t } from "i18next";
import "./LocateFilter.scss";

const LocateFilter = () => {
  const {
    provinces,
    selectedProvince,
    handleProvinceChange,
    districts,
    selectedDistrict,
    handleDistrictChange,
    wards,
    selectedWard,
    setSelectedWard,
  } = useContext(LocateContext);

  return (
    <div className="selected-locate">
      <FormControl sx={{ m: 1, minWidth: 40, width: "30%" }}>
        <InputLabel
          id="select-province-label"
          style={{ zIndex: "80", paddingRight: "5px" }}
        >
          Tỉnh thành
        </InputLabel>
        <Select
          labelId="select-province-label"
          id="demo-simple-select-province-helper"
          value={selectedProvince}
          label="Age"
          onChange={handleProvinceChange}
        >
          <MenuItem value="">
            <em>None</em>
          </MenuItem>
          {provinces?.map((province, index) => (
            <MenuItem
              value={province.provinceName}
              style={{ fontSize: "15px" }}
              key={index}
            >
              {province.provinceName}
            </MenuItem>
          ))}
        </Select>
        <FormHelperText sx={{ fontSize: "10px" }}>
          {t("contentMess.province")}
        </FormHelperText>
      </FormControl>

      <FormControl sx={{ m: 1, minWidth: 40, width: "30%" }}>
        <InputLabel
          id="select-district-label"
          style={{ zIndex: "80", paddingRight: "5px" }}
        >
          Quận/Huyện
        </InputLabel>
        <Select
          labelId="select-district-label"
          id="demo-simple-select-district-helper"
          value={selectedDistrict}
          label="Age"
          onChange={handleDistrictChange}
        >
          <MenuItem value="">
            <em>None</em>
          </MenuItem>
          {districts?.map((district, index) => (
            <MenuItem
              value={district.districtName}
              style={{ fontSize: "15px" }}
              key={index}
            >
              {district.districtName}
            </MenuItem>
          ))}
        </Select>
        <FormHelperText sx={{ fontSize: "10px" }}>
          {t("contentMess.province")}
        </FormHelperText>
      </FormControl>

      <FormControl sx={{ m: 1, minWidth: 40, width: "30%" }}>
        <InputLabel
          id="select-ward-label"
          style={{ zIndex: "80", paddingRight: "5px" }}
        >
          Phường/Xã
        </InputLabel>
        <Select
          labelId="select-ward-label"
          id="demo-simple-select-ward-helper"
          value={selectedWard}
          label="Age"
          onChange={(e) => setSelectedWard(e.target.value)}
        >
          <MenuItem value="">
            <em>None</em>
          </MenuItem>
          {wards?.map((ward, index) => (
            <MenuItem
              value={ward.wardName}
              style={{ fontSize: "15px" }}
              key={index}
            >
              {ward.wardName}
            </MenuItem>
          ))}
        </Select>
        <FormHelperText sx={{ fontSize: "10px" }}>
          {t("contentMess.province")}
        </FormHelperText>
      </FormControl>
    </div>
  );
};
export default LocateFilter;

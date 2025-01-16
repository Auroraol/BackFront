import components from "@/components";
import filters from "@/filters";
import pages from "@/pages";
import views from "@/views";
import store from "@/store/tools";
import service from "@/api";
import directives, { checkPerm } from "@/directives";
import { iconList } from "@/common";
import "@/assets/css/index.scss";

export { iconList, checkPerm };
export default { components, filters, pages, views, store, service, directives };

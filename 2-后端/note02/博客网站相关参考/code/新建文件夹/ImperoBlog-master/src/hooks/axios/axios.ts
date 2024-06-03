import axios from "axios";
import { useStore } from '../../store/count'

const pinia = useStore()


const useAxios = axios.create({
    baseURL: `${pinia.apiRoot}/api`,
})


export default useAxios
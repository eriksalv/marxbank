import axios, {AxiosPromise, AxiosInstance, AxiosRequestHeaders} from 'axios';

export function promiseWrapper<T>(axios: AxiosPromise<T>): Promise<T> {
    return new Promise((resolve, reject) => {
        axios
        .then(d => resolve(d.data))
        .catch(async error => {
            if (error.response) {
                console.error(error.response.status);
                reject(error.response.data.message);
            } else {
                reject(error.message);
            }
        })
    })
}

export class RestService {

    private readonly handler: AxiosInstance;
    // lage en axios objekt
    public constructor() {
        this.handler = axios.create();
        this.handler.defaults.baseURL = "/";
    }

    public setUrl(url: string) {
        this.handler.defaults.baseURL = url;
    }

    public setToken(token: string | null) {
        if (token === null) delete this.handler.defaults.headers.common["Authorization"];
        else this.handler.defaults.headers.common["Authorization"] = `Bearer:${token}`;
    }

    // hente GETS
    public get<T> (url: string, config?: {params?: AxiosRequestHeaders, headers?: AxiosRequestHeaders}): Promise<T> {
        return promiseWrapper(this.handler.get(url, {validateStatus: (code: number) => code === 200, ...config}));
    }
    // POSTS
    public post (url: string, data?: object): Promise<void> {
        return promiseWrapper(this.handler.post(url, data, {validateStatus: (code: number) => (code === 200 || code === 201 || code === 204)}));
    }
    // trengs denne?
    public postData<T> (url: string, data?: object): Promise<T> {
        return promiseWrapper(this.handler.post(url, data, {validateStatus: (code: number) => code === 200}));
    }

    // PUTS
    public put (url: string, data?: object): Promise<void> {
        return promiseWrapper(this.handler.put(url, data, {validateStatus: (code: number) => (code === 200 || code === 204)}));
    }

    // DELETES
    public delete (url: string): Promise<void> {
        return promiseWrapper(this.handler.delete(url, {validateStatus: (code: number) => (code === 200 || code === 202 || code === 204)}));
    }
}
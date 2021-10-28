export interface LoginRequest {
    username: string,
    password: string
}

export interface SignUpRequest {
    username: string,
    email: string,
    password: string
}

export interface LoginResponse {
    userId: number,
    token: string
}

export interface Userstate {
    userId: number,
    token: String
}
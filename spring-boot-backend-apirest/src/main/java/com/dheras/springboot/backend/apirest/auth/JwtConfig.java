package com.dheras.springboot.backend.apirest.auth;

public class JwtConfig {
	public static final String LLAVE_SECRETA = "alguna.clave.secreta.12345678";
	
	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n"
			+ "MIIEpQIBAAKCAQEAp0WINFAHa0YCKkqgqohAOcmrgnr6bSirgO8DNSmmjlD8Sm47\r\n"
			+ "U7+EFwEO4kqMEZ8yE38/LO6hPLzcNVfHlsxb6re2JF1hk5FqE7rZpLpgfStKOaOv\r\n"
			+ "ee65fXF0BgIYE4QYh0mOZ8JE2zfAe5jXkDVqj+4DEdDYeINYC6CBhxrBNBe1IOtU\r\n"
			+ "XV3hz4SanMYus+BU+QVRe0KzwjnFvQGVKXNk6xB99bZQpiaYVFwyjDS8+qycIQzN\r\n"
			+ "9fjTwIMs2D6b9k0/kp6v8ftEJHWp4Nt+m22rWMhc/cJ1+O4kXjFNbLG85mg+NuPz\r\n"
			+ "FM1meolAQkMC6AwqC25ib4c3oC6fiKepoehNawIDAQABAoIBAQCEFnxAExJIu7fp\r\n"
			+ "/ZRyFbXh7EbreoFWwc7sudEmogVMTNdtTy66K3n7UHKYb5W8NKIuk0UoDih09g5O\r\n"
			+ "cUrC4g2ThA7pGHR9O08w1VQYqLSZIx4x9fgTqVgAnx6nv++TObTE4Gj7toT5N0Vg\r\n"
			+ "VImk7wl/LsxtvSWzxMQ6Kj0emxoGL4rVnnKklMYu8c7igkgcVd0nrcxY9+amVS2l\r\n"
			+ "bVYVc/oHQSJ+CjWRBmAI9x8KSlFtWmQXVYLLWN/vthM9E0WsQtEAYDeW4muud/Le\r\n"
			+ "3Y2FPbQ8CKAwOzsMQmHftjKtGfGcVgq5vKh8axzjYS4MEni9Fhv4t+4nwXB6EVix\r\n"
			+ "KjN94y/BAoGBAN3LEIxVmzIilXbK4IDuhq55iu7cpT0SefHT1PaFmyelYuTF1uv0\r\n"
			+ "Ss2tHZcSOngtUxmXMbaLXAj6x8kmXPd/+uUBTHn3vMTKRQxF9tTw3t5ZMjZi2h+S\r\n"
			+ "dELcwMSaRIlIqCCKxASjJGDVRjl1Y8nIS6N1eDOEy5+sq7d2EL1E5Uf1AoGBAMER\r\n"
			+ "0lnX1w2Gbedy/pjX64zJlZd+5VSphHK0r26LavC4qaHlsFzDm4TtplQ8yl3ajFrC\r\n"
			+ "PWu0FP/qn8rOsrwCubugE3Kbu6fAlniH2zj8sjYs/iDxHvjZ9r55DLf6IqbcwMDz\r\n"
			+ "/jQ025cf6MCwnTDlHCwgfRgp7vVn93nZMaHOg8PfAoGAYJwqeiZz8rOzzUnz3tWH\r\n"
			+ "nwpcZU2BQIgCzD7MfhGWkv9sg2BWDPOdfdfRktOi2mRL1VLRmcU8csDiM5FdX4XE\r\n"
			+ "wOn+6QXrmQDOcNks+nZtbgcGY0BN6QC/WbyUz06vWelnKr2nQqnZl8Bu1Wp37BYu\r\n"
			+ "NGZf1HkcxVHOfpyUEA2pnd0CgYEAmvCkcPloWjBRrvZ4H0zYecmkxUlO3ZyU8EDU\r\n"
			+ "sNAt6DUp3QUpfah0/D6DxwJqHNuw230aMX75g09VITqz8XH2f/483TLhbASAPQY6\r\n"
			+ "Bge1NQjygJlAk9DH1Z/rdxHEEUkDX1Xv4RGyUBuJfGnAHGchLsB745JzdHoU/rIw\r\n"
			+ "xHuo/t0CgYEAhDFpbcw/5hgpH1HcxjclNpl4qDUZXa+CBoktujr/o/CTBltYESao\r\n"
			+ "np0qFM8SAdDHKFVphmx/yOhEqviEl8ejSly+2HAIBOx+VraDMaQCFZPyZvdo6QDU\r\n"
			+ "/4Cy8I6TcxeOYk5nYj6f8M8a5xY7wD3l3D8CUIe9dEXiaOJvqw3wFDw=\r\n"
			+ "-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp0WINFAHa0YCKkqgqohA\r\n"
			+ "Ocmrgnr6bSirgO8DNSmmjlD8Sm47U7+EFwEO4kqMEZ8yE38/LO6hPLzcNVfHlsxb\r\n"
			+ "6re2JF1hk5FqE7rZpLpgfStKOaOvee65fXF0BgIYE4QYh0mOZ8JE2zfAe5jXkDVq\r\n"
			+ "j+4DEdDYeINYC6CBhxrBNBe1IOtUXV3hz4SanMYus+BU+QVRe0KzwjnFvQGVKXNk\r\n"
			+ "6xB99bZQpiaYVFwyjDS8+qycIQzN9fjTwIMs2D6b9k0/kp6v8ftEJHWp4Nt+m22r\r\n"
			+ "WMhc/cJ1+O4kXjFNbLG85mg+NuPzFM1meolAQkMC6AwqC25ib4c3oC6fiKepoehN\r\n"
			+ "awIDAQAB\r\n"
			+ "-----END PUBLIC KEY-----";

}

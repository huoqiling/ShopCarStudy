package com.zhangxin.study.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;
import com.zhangxin.study.utils.Base64Utils;
import com.zhangxin.study.utils.LogUtil;
import com.zhangxin.study.utils.RSAEncrypt;
import com.zhangxin.study.utils.RSAUtils;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RSAActivity extends BaseActivity {

    /* 密钥内容 base64 code */
    private static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGuLhWpdw3Dq5iIunbOAqsFb8K\r" +
            "Iaxt7jOf18KFVh6xIyh+6qvvvSpuEoRuzfoXuhdX2AccjVzNFIdW09m5y7RlwfEP\r" +
            "S287/4LDEmAJed62hqPtZAGbblWlWBpgphvlf6lT6NLgnCAuv/O9xSnIf2JgZD4v\r" +
            "zKgGWkCJCnblyXc7DwIDAQAB";
    private static String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALUebbLVZ7ZrNRy+NyIGsIlU/iaXfvHEeZa/V8U5kS3VCjl/EeLO/M/KmS50muNrSj7Y6dmoU92VtI+b62LryJZ9gCr5tm+asMcln4pXHX1qJBbSaCI9zVIv7wTUEfaFTiU1OQ7yEYw/dpZd1HzXoyNYa4HOYd2kxpj4NkxgDL87AgMBAAECgYEAsiDuFK8r5E4GG3G8eXuIzlhH6R6gHBl219rx8KfQb/oRp0ChD6cxHxALnZPS7Y4MpEjjnUyextRAk+juFQj96dHHVvn9lvJI63KdEM/2oN5HJqoKXCkvA07epm1WXIAvug3gyD0ERh/I2ISeWmzR6nBN1cFpP5c1gk+WPE4KW4kCQQDxfCRl38fTIy8uhTuTUXZxeTUoo2B9etIoOr4yeFBq/PlYQiqRP0ZSmCBe2vMSTkoqAkLPbKTItmlLXIc+JGxfAkEAwAFmvZJQQhs6ThMLrHcQXnGg9l4vbOUKU82og/GhyYvghDI3xiy9zQDvQ5VgyRdiYYndrcZpMLBu0mbJtfbapQJAW47F/hoqmVLtwKWjQOfSEJP0I8ROAUVgCQC8vEYBt47SuzPbD9b9wrMLEuvQ3U/xrpvM2PhIQhFnOruKS3Vj4QJAPxm2zCJjofYUyUfVFE7k4ETzo0P8zgrPxA7YjNRCWVxQ4uEmF0jo75CZEVrsPeojeO64Hb4RH+E76oXBUL3ZyQJAFwxL6kRNzpIEchtPV/z7V/aGtCkzaOGeSKErX4+5O2eFuHau3xB+itwTKSx3gNr4jH2alkOiLS64Z2NtnO5HFw==";




    @BindView(R.id.btnEncryption)
    TextView btnEncryption;

    @BindView(R.id.btnDecryption)
    TextView btnDecryption;

    @BindView(R.id.etSource)
    EditText etSource;

    @BindView(R.id.tvEncryptContent)
    TextView tvEncryptContent;//加密内容

    @BindView(R.id.tvDecryptContent)
    TextView tvDecryptContent;//解密内容

    @BindView(R.id.btnKeyPair)
    TextView btnKeyPair;

    @BindView(R.id.tvKeyPair)
    TextView tvKeyPair;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rsa;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnEncryption, R.id.btnDecryption, R.id.btnKeyPair})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEncryption://加密
                try {
                    String source = etSource.getText().toString().trim();
                    PublicKey publicKey = RSAEncrypt.getPublicKey(PUCLIC_KEY);
//                    byte[] encryptByte = RSAUtils.encryptData(source.getBytes(), publicKey);

                    // 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
//                    String afterEncrypt = Base64Utils.encode(encryptByte);
                    String afterEncrypt = RSAEncrypt.encrypt(source,publicKey);
                    tvEncryptContent.setText("加密内容："+afterEncrypt);
                    LogUtil.zhangx("加密内容:"+tvEncryptContent.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnDecryption://解密
                try {
//                    String encryptContent = tvEncryptContent.getText().toString().trim();
                    String encryptContent = "Me+xBuFDAIewPW3BAdUPthyiWVVa9nm9SUBvXFB2I8Gzg8v5qdsGGtGtfva1JZw3\r" +
                            "WY9SxRv153DTs7Ay4GKbH2oQ40l05CyyAIKFfm1NAU1h5vWuIZ8GFrCE25UffZfp\r" +
                            "cGp5N96PD1gX/w3pV+aJWKsQEFK3ak0heBk/XioF3xGZi8zyycwaqn2dIxq1rRFd\r" +
                            "75oJVqL6AEkqKd9LSatHD6+DX1pk1sDZfzqdDaXPu9uoM0uZBlVb6N2zkE6iLW3P\r" +
                            "36g4r4AUFrgHiKNz/sgX0Ai7HshJXCZ9UR53YIUj27/THi4bQYRv4xPH5QyMhmbY\r" +
                            "i+SHFuqJ8ZK3LtY7/xk/OAQ+DBHMM4SP0cbfHIuJ5kRsiw4J8e3EobZjBOKw2ehp\r" +
                            "yaK7YGGUmVZIOo2mGnAUS8Oi6rKcPcqgwwVq3IvSuOHnp9XA9zaS6HqCZGSbFz0C\r" +
                            "SrxSfihl2pisahd8yjHOkTnJBEzZaRR3P5hyRDCucFdHB4tBxUMZaXMG6fYK/wpb\r" +
                            "Y9rowRaB+fQVl9ee5oRad2eptCkVvZWF2YBdXyFEIYDnlGyvezms6o7v09meGNVG\r" +
                            "jX9I1dqQWXSEr0SPSxmBvTDi0l19sN+uAA7j2udvDtRcNWahWwBfOtSUU2mSG+lc\r" +
                            "6hOc6CjGraGPpkWfb58D2MQ2S9dJq6d7cFvR/2nu650=";
                    PrivateKey privateKey = RSAEncrypt.getPrivateKey(PRIVATE_KEY);
                    // 因为RSA加密后的内容经Base64再加密转换了一下，所以先Base64解密回来再给RSA解密
//                    byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(encryptContent), privateKey);

                    String decryptStr = RSAEncrypt.decrypt(encryptContent,privateKey);
                    tvDecryptContent.setText("解密内容："+decryptStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btnKeyPair:
                KeyPair keyPair = RSAUtils.generateRSAKeyPair();
                byte[] publicKey = RSAUtils.getPublicKey(keyPair);
                byte[] privateKey = RSAUtils.getPrivateKey(keyPair);
                PUCLIC_KEY = Base64Utils.encode(publicKey);
                PRIVATE_KEY = Base64Utils.encode(privateKey);
                tvKeyPair.setText("公钥：" + PUCLIC_KEY + "\n\n" + "私钥：" + PRIVATE_KEY);
                LogUtil.zhangx("公钥："+PUCLIC_KEY);
                LogUtil.zhangx("私钥："+PRIVATE_KEY);
                break;
        }
    }
}

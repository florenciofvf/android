package br.com.florencio.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.ViewGroup.LayoutParams;

public class CalcActivity extends AppCompatActivity {
    private final AbstratoAcao[][] ACOES = {
            {new DigitoAcao("7"), new DigitoAcao("8"), new DigitoAcao("9"), new OperadorAcao("+")},
            {new DigitoAcao("4"), new DigitoAcao("5"), new DigitoAcao("6"), new OperadorAcao("-")},
            {new DigitoAcao("1"), new DigitoAcao("2"), new DigitoAcao("3"), new OperadorAcao("x")},
            {new ZeroAcao(), new VirgulaAcao(), new IgualAcao(), new OperadorAcao("/")}
    };

    private final byte MAXIMO_CARACTER_OPERANDO = 15;
    private final String VAZIO = "";

    private TextView txtDisplay;
    private Button btnComecar;
    private Button btnVoltar;

    private StringBuilder operandoEsquerdo;
    private StringBuilder operandoDireito;
    private String operador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layoutPrincipal = new LinearLayout(this);
        layoutPrincipal.setOrientation(LinearLayout.VERTICAL);
        layoutPrincipal.setLayoutParams(criarParams(0));

        LinearLayout linhaDisplay = new LinearLayout(this);
        linhaDisplay.setLayoutParams(criarParams(1));
        linhaDisplay.setGravity(Gravity.RIGHT);
        linhaDisplay.setPadding(0, 0, 10, 0);

        txtDisplay = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        txtDisplay.setLayoutParams(params);
        txtDisplay.setTextSize(22);
        linhaDisplay.addView(txtDisplay);

        layoutPrincipal.addView(linhaDisplay);

        btnComecar = new Button(this);
        btnComecar.setLayoutParams(criarParams(1));
        btnComecar.setText(R.string.label_comecar);
        btnComecar.setOnClickListener(new ComecarAcao());

        btnVoltar = new Button(this);
        btnVoltar.setLayoutParams(criarParams(1));
        btnVoltar.setText(R.string.label_voltar);
        btnVoltar.setOnClickListener(new VoltarAcao());

        LinearLayout linhaControle = new LinearLayout(this);
        linhaControle.setLayoutParams(criarParams(1));
        linhaControle.addView(btnComecar);
        linhaControle.addView(btnVoltar);

        layoutPrincipal.addView(linhaControle);

        for(AbstratoAcao[] linhaAcoes : ACOES) {
            LinearLayout linhaBotoes = new LinearLayout(this);
            linhaBotoes.setLayoutParams(criarParams(1));

            for(AbstratoAcao acao : linhaAcoes) {
                linhaBotoes.addView( criarBotao(acao) );
            }

            layoutPrincipal.addView(linhaBotoes);
        }

        setContentView(layoutPrincipal);

        limparDisplay();
    }

    private Button criarBotao(AbstratoAcao acao) {
        Button b = new Button(this);
        b.setLayoutParams(criarParams(1));
        b.setOnClickListener(acao);
        b.setText(acao.titulo);

        return b;
    }

    private LayoutParams criarParams(float peso) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, peso);
        params.setMargins(-7, -4, -7, -4);
        return params;
    }

    private void mensagem(int s) {
        Toast.makeText(this, s,  Toast.LENGTH_SHORT).show();
    }

    private void adicionarDigito(StringBuilder builder, byte MAXIMO_CARACTER, char c) {
        if(builder.length() < MAXIMO_CARACTER) {
            builder.append(c);
        } else {
            mensagem(R.string.msg_maximo_caracter);
        }
    }

    private void atualizarDisplay() {
        txtDisplay.setText(operandoEsquerdo.toString() + operador + operandoDireito.toString());
    }

    private void limparDisplay() {
        operandoEsquerdo = new StringBuilder();
        operandoDireito = new StringBuilder();
        operador = VAZIO;
        atualizarDisplay();
    }

    private StringBuilder getOperando() {
        return operador.length() == 0 ? operandoEsquerdo : operandoDireito;
    }

    private abstract class AbstratoAcao implements View.OnClickListener {
        protected String titulo;

        AbstratoAcao(String titulo) {
            this.titulo = titulo;
        }
    }

    private class DigitoAcao extends AbstratoAcao {
        DigitoAcao(String titulo) {
            super(titulo);
        }

        public void onClick(View v) {
            Button b = (Button)v;
            StringBuilder operando = getOperando();
            adicionarDigito(operando, MAXIMO_CARACTER_OPERANDO, b.getText().charAt(0));
            atualizarDisplay();
        }
    }

    private class OperadorAcao extends AbstratoAcao {
        OperadorAcao(String titulo) {
            super(titulo);
        }

        public void onClick(View v) {
            if(operandoEsquerdo.length() == 0) {
                return;
            }

            Button b = (Button)v;
            operador = b.getText().toString();
            atualizarDisplay();
        }
    }

    private class ComecarAcao extends AbstratoAcao {
        ComecarAcao() {
            super(null);
        }

        public void onClick(View v) {
            limparDisplay();
        }
    }

    private class VoltarAcao extends AbstratoAcao {
        VoltarAcao() {
            super(null);
        }

        public void onClick(View v) {
            if(operandoDireito.length() > 0) {
                operandoDireito.delete(operandoDireito.length() -1, operandoDireito.length());

            } else if(operador.length() > 0) {
                operador = "";

            } else if(operandoEsquerdo.length() > 0) {
                operandoEsquerdo.delete(operandoEsquerdo.length() - 1, operandoEsquerdo.length());

            }

            atualizarDisplay();
        }
    }

    private class ZeroAcao extends AbstratoAcao {
        ZeroAcao() {
            super("0");
        }

        public void onClick(View v) {
            StringBuilder operando = getOperando();

            if(operando.length() > 0) {
                adicionarDigito(operando, MAXIMO_CARACTER_OPERANDO, titulo.charAt(0));
                atualizarDisplay();
            }
        }
    }

    private class VirgulaAcao extends AbstratoAcao {
        VirgulaAcao() {
            super(",");
        }

        public void onClick(View v) {
            StringBuilder operando = getOperando();

            if(operando.length() > 0 && operando.indexOf(titulo) == -1) {
                adicionarDigito(operando, MAXIMO_CARACTER_OPERANDO, titulo.charAt(0));
                atualizarDisplay();
            }
        }
    }

    private class IgualAcao extends AbstratoAcao {
        IgualAcao() {
            super("=");
        }

        public void onClick(View v) {
            if(operandoEsquerdo.length() == 0 || operandoDireito.length() == 0) {
                return;
            }

            Number resposta = null;
            Number operandoE = criarNumber(operandoEsquerdo);
            Number operandoD = criarNumber(operandoDireito);

            switch(operador) {
                case "+": resposta = somar(operandoE, operandoD); break;
                case "-": resposta = subtrair(operandoE, operandoD); break;
                case "x": resposta = multiplicar(operandoE, operandoD); break;
                case "/":
                    if(operandoD.doubleValue() == 0.0) {
                        mensagem(R.string.msg_divisao_por_zero);
                    } else {
                        resposta = dividir(operandoE, operandoD);
                    }
            }

            if(resposta != null) {
                String string = resposta.toString().replace('.', ',');
                limparDisplay();
                operandoEsquerdo.append(string);
                atualizarDisplay();
            }
        }

        private Number criarNumber(StringBuilder builder) {
            String string = builder.toString().replace(',', '.');

            if(string.indexOf(".") >= 0) {
                return new Double(string);
            }else {
                return new Long(string);
            }
        }

        private Number somar(Number op1, Number op2) {
            if(op1 instanceof Long) {
                if(op2 instanceof Long) {
                    return new Long(op1.longValue() + op2.longValue());
                } else{
                    return new Double(op1.longValue() + op2.doubleValue());
                }
            }
            return new Double(op1.longValue() + op2.doubleValue());
        }

        private Number subtrair(Number op1, Number op2) {
            if(op1 instanceof Long) {
                if(op2 instanceof Long) {
                    return new Long(op1.longValue() - op2.longValue());
                } else{
                    return new Double(op1.longValue() - op2.doubleValue());
                }
            }
            return new Double(op1.longValue() - op2.doubleValue());
        }

        private Number multiplicar(Number op1, Number op2) {
            if(op1 instanceof Long) {
                if(op2 instanceof Long) {
                    return new Long(op1.longValue() * op2.longValue());
                } else{
                    return new Double(op1.longValue() * op2.doubleValue());
                }
            }
            return new Double(op1.longValue() * op2.doubleValue());
        }

        private Number dividir(Number op1, Number op2) {
            if(op1 instanceof Long) {
                if(op2 instanceof Long) {
                    return new Long(op1.longValue() / op2.longValue());
                } else{
                    return new Double(op1.longValue() / op2.doubleValue());
                }
            }
            return new Double(op1.longValue() / op2.doubleValue());
        }
    }
}
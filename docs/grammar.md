$$
\begin{align}

    [\text{Program}] &\to [\text{Statement}] ^* \\
    
    [\text{Statement}] &\to

    \begin{cases}
        [\text{Object}] [\text{Action}] [\text{Object}] [\text{Object Name}] [\text{*Optional* Value}]\\
        [\text{Object}] \text{ HELP} \\
        [\text{End}] \\
    \end{cases} \\

    [\text{Action}] &\to

    \begin{cases}
        [\text{Add}] \\
        [\text{Remove}] \\
        [\text{Modify}] \\
    \end{cases}\\

    [\text{Object}] &\to
    
    \begin{cases}
        [\text{WSS}] \\
        [\text{Port}] \\
        [\text{Transponder Card}]\\
        [\text{Transponder}]\\
        [\text{Fiber}] \\
    \end{cases} \\

    [\text{Object Name}] &\to String \\

    [\text{*Optional* Value}] &\to String \\

    \end{align}
$$
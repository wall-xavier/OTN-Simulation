$$
\begin{align}

    [\text{Program}] &\to [\text{Statement}] ^* \\
    
    [\text{Statement}] &\to

    \begin{cases}
        [\text{Device}] [\text{Action}] [\text{Device Name}] [\text{Object}] [\text{Object Name}] [\text{*Optional* Value}]\\
        [\text{Device}] [\text{Device Name}] [\text{Action}] [\text{Field}] [\text{Value}] \\
        [\text{Device}] \text{ HELP} \\
        \text{HELP} \\
        \text{END} \\
    \end{cases} \\

    [\text{Device}] &\to [\text{Object}] \\

    [\text{Action}] &\to

    \begin{cases}
        [\text{Add}] \\
        [\text{Remove}] \\
        [\text{Modify}] \\
        [\text{Set}] \\
        [\text{CREATE}]
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

    [\text{FIELD}] &\to
    
    \begin{cases}
        [\text{SPEED}] \\
        [\text{DUPLEX}] \\ 
        [\text{TX\_POWER}] \\
        [\text{PORT\_COUNT}] \\ 
        [\text{LABEL}] \\
        [\text{PORT\_INDEX}] \\
        [\text{LENGTH}] \\
        [\text{A\_SIDE}] \\
        [\text{Z\_SIDE}] \\
        [\text{WAVELENGTH}] \\
        [\text{FREQUENCY}] \\
    \end{cases}\\

\end{align}
$$
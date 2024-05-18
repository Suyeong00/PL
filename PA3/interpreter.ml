module F = Format

(* interp_expr: 주어진 식을 해석하여 값을 반환하는 함수 *)
let rec interp_expr (e: Ast.expr) (g: FStore.t) (s: Store.t) : Value.t =
  match e with
  | Ast.Num n -> Value.NumV n
  | Ast.Add (e1, e2) ->
      let v1 = interp_expr e1 g s in
      let v2 = interp_expr e2 g s in
      (match (v1, v2) with
      | (Value.NumV n1, Value.NumV n2) -> Value.NumV (n1 + n2))
  | Ast.Sub (e1, e2) ->
      let v1 = interp_expr e1 g s in
      let v2 = interp_expr e2 g s in
      (match (v1, v2) with
      | (Value.NumV n1, Value.NumV n2) -> Value.NumV (n1 - n2))
  | Ast.Id x ->
      if Store.mem x s then Store.find x s
      else failwith ("Free identifier: " ^ x)
  | Ast.LetIn (x, e1, e2) ->
      let v1 = interp_expr e1 g s in
      let new_s = Store.add x v1 s in
      interp_expr e2 g new_s
  | Ast.Call (f, args) ->
      if FStore.mem f g then
        let (params, body) = FStore.find f g in
        if List.length params = List.length args then
          let arg_vals = List.map (fun arg -> interp_expr arg g s) args in
          let new_s = List.fold_left2 (fun acc param arg_val -> Store.add param arg_val acc) s params arg_vals in
          interp_expr body g new_s
        else
          failwith ("The number of arguments of " ^ f ^ " mismatched: Required: " ^ string_of_int (List.length params) ^ ", Actual: " ^ string_of_int (List.length args))
      else
        failwith ("Undefined function: " ^ f)

(* interp_fundef: 주어진 함수 정의를 해석하여 함수 저장소에 추가하는 함수 *)
let interp_fundef (d: Ast.fundef) (g: FStore.t) : FStore.t =
  match d with
  | Ast.FunDef (f, params, body) -> FStore.add f (params, body) g

(* interp: 주어진 프로그램을 해석하여 최종 값을 반환하는 함수 *)
let interp (p: Ast.prog) : Value.t =
  match p with
  | Ast.Prog (fundefs, main_expr) ->
      let g = List.fold_left (fun acc fundef -> interp_fundef fundef acc) FStore.empty fundefs in
      interp_expr main_expr g Store.empty
